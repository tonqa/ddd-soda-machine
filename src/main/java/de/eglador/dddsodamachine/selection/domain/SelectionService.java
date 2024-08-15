package de.eglador.dddsodamachine.selection.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonOffEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOnEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.mixing.application.MixerService;
import de.eglador.dddsodamachine.mixing.domain.MixingFinishedEvent;
import de.eglador.dddsodamachine.recipe.application.RecipeService;
import de.eglador.dddsodamachine.recipe.domain.RecipeComposite;
import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import de.eglador.dddsodamachine.price.application.RecipePriceService;
import de.eglador.dddsodamachine.pay.application.RegisterService;
import de.eglador.dddsodamachine.pay.domain.CoinsInsertionCanceledEvent;
import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class SelectionService {

    private final ApplicationEventPublisher eventPublisher;
    private final RecipeService recipeService;
    private final MixerService mixerService;
    private final RegisterService registerService;
    private final RecipePriceService recipePriceService;
    private final SelectionStateRepository selectionStateRepository;

    public SelectionService(ApplicationEventPublisher eventPublisher, RecipeService recipeService, MixerService mixerService,
                            RegisterService registerService, RecipePriceService recipePriceService,
                            SelectionStateRepository selectionStateRepository) {
        this.eventPublisher = eventPublisher;
        this.recipeService = recipeService;
        this.mixerService = mixerService;
        this.registerService = registerService;
        this.recipePriceService = recipePriceService;
        this.selectionStateRepository = selectionStateRepository;
    }

    public boolean selectRecipe(Long automatonId, int selection) {
        SelectionState state = getOrCreate(automatonId);
        if (!state.isRecipeCanBeSelected()) {
            return false;
        }
        if (!state.getChoiceToRecipeId().containsKey(selection)) {
            return false;
        }
        RecipeId recipeId = state.getChoiceToRecipeId().get(selection);
        if (!mixerService.hasEnoughFill(automatonId, recipeId)) {
            return false;
        }
        RecipePriceId recipePriceId = recipePriceService.recipePriceIdByRecipe(recipeId);
        if (!registerService.hasEnoughSpace(automatonId, recipePriceId)) {
            return false;
        }
        this.selectionStateRepository.save(state);
        this.eventPublisher.publishEvent(new RecipeSelectedEvent(
                this, automatonId, recipeId));
        return true;
    }

    @EventListener
    public void handle(AutomatonOnEvent readyEvent) {
        this.initRecipeSelection(readyEvent.getAutomatonId());
    }

    @EventListener
    public void handle(AutomatonOffEvent offEvent) {
        SelectionState state = getOrCreate(offEvent.getAutomatonId());
        state.setRecipeCanBeSelected(false);
        this.selectionStateRepository.save(state);
    }

    @EventListener
    public void handle(CoinsInsertionCanceledEvent event) {
        this.initRecipeSelection(event.getAutomatonId());
    }

    @EventListener
    public void handle(MixingFinishedEvent event) {
        this.initRecipeSelection(event.getAutomatonId());
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent event) {
        this.selectionStateRepository.deleteAllById(event.getAutomatonIds());
    }

    private SelectionState getOrCreate(Long automatonId) {
        Optional<SelectionState> state = this.selectionStateRepository.findById(automatonId);
        if (state.isPresent()) {
            return state.get();
        }
        SelectionState selectionState = new SelectionState();
        selectionState.setAutomatonId(automatonId);
        selectionState.setChoiceToRecipeId(new HashMap<>());
        selectionState.setRecipeCanBeSelected(true);
        return selectionState;
    }

    private void initRecipeSelection(Long automatonId) {
        SelectionState state = getOrCreate(automatonId);
        Iterable<RecipeComposite> recipes = this.recipeService.recipes();
        state.initSelectionState(recipes);
        this.selectionStateRepository.save(state);
    }
}
