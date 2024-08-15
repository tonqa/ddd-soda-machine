package de.eglador.dddsodamachine.mixing.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonCreatedEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.pay.domain.CoinInsertionFinishedEvent;
import de.eglador.dddsodamachine.recipe.application.RecipeService;
import de.eglador.dddsodamachine.recipe.domain.*;
import de.eglador.dddsodamachine.selection.domain.RecipeSelectedEvent;
import de.eglador.dddsodamachine.mixing.application.MixerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MixerServiceImpl implements MixerService {

    Logger LOG = LoggerFactory.getLogger(MixerServiceImpl.class);

    private final MixerRepository mixerRepository;
    private final RecipeService recipeService;

    public MixerServiceImpl(MixerRepository mixerRepository, RecipeService recipeService) {
        this.mixerRepository = mixerRepository;
        this.recipeService = recipeService;
    }

    public boolean hasEnoughFill(Long automatonId, RecipeId recipeId) {
        RecipeComposite recipe = recipeService.recipeById(recipeId).orElseThrow();
        return mixerRepository.findById(automatonId)
                .map(mixer -> mixer.hasEnoughFillForRecipe(recipe))
                .orElse(false);
    }

    @EventListener
    public void handle(AutomatonCreatedEvent event) {
        Mixer mixer = new Mixer();
        mixer.initMixer(event.getAutomatonId(), null);
        Mixer newMixer = this.mixerRepository.save(mixer);
        LOG.info("New mixer created: {}", newMixer);
    }

    @EventListener
    public void handle(RecipeSelectedEvent event) {
        this.mixerRepository.findById(event.getAutomatonId())
                .ifPresent(mixer -> {
                    mixer.setSelectedRecipe(event.getRecipeId());
                    this.mixerRepository.save(mixer);
                });
    }

    @EventListener
    public void handle(CoinInsertionFinishedEvent event) {
        this.mixerRepository.findById(event.getAutomatonId())
                .ifPresent(mixer -> {
                    if (mixer.getSelectedRecipe() == null) {
                        return;
                    }
                    RecipeComposite recipe = recipeService.recipeById(mixer.getSelectedRecipe()).orElseThrow();
                    List<Future<Boolean>> futures = mixer.mixRecipe(recipe);
                    this.mixerRepository.save(mixer);
                    try {
                        for (Future<Boolean> future : futures) {
                            future.get();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    mixer.finishMixing(recipe);
                    this.mixerRepository.save(mixer);
                });
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent automatonsDeletedEvent) {
        this.mixerRepository.deleteAllById(automatonsDeletedEvent.getAutomatonIds());
    }

}