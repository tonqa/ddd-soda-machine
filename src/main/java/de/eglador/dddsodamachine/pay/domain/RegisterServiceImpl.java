package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.pay.application.RegisterService;
import de.eglador.dddsodamachine.price.domain.RecipePrice;
import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import de.eglador.dddsodamachine.price.domain.RecipePriceRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

public class RegisterServiceImpl implements RegisterService {
    private final RecipePriceRepository recipePriceRepository;
    private final RegisterRepository registerRepository;
    private final RegisterFactory registerFactory;

    public RegisterServiceImpl(RecipePriceRepository recipePriceRepository,
                               RegisterRepository registerRepository,
                               RegisterFactory registerFactory) {
        this.recipePriceRepository = recipePriceRepository;
        this.registerRepository = registerRepository;
        this.registerFactory = registerFactory;
    }

    @EventListener
    public void handle(CoinInsertionFinishedEvent event) {
        List<CoinType> payed = event.getPayedCoinsYet();
        Register register = getOrCreate(event.getAutomatonId());
        register.addCoinsAndCashback(payed, event.getOverPayed());
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent event) {
        this.registerRepository.deleteAllById(event.getAutomatonIds());
    }

    public boolean hasEnoughSpace(Long automatonId, RecipePriceId recipePriceId) {
        RecipePrice recipePrice = this.recipePriceRepository.findRecipePriceById(recipePriceId);
        Register register = getOrCreate(automatonId);
        return register.allCompartmentsHaveCapacity(recipePrice);
    }

    private Register getOrCreate(Long automatonId) {
        return this.registerRepository.findById(automatonId)
                .orElseGet(() -> registerFactory.createRegister(automatonId));
    }
}