package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonCreatedEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOffEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOnEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingFinishedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingStartedEvent;
import de.eglador.dddsodamachine.selection.domain.RecipeSelectedEvent;
import de.eglador.dddsodamachine.price.domain.RecipePrice;
import de.eglador.dddsodamachine.price.domain.RecipePriceRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoneySlotService {
    private final RecipePriceRepository recipePriceRepository;
    private final MoneySlotRepository moneySlotRepository;

    public MoneySlotService(RecipePriceRepository recipePriceRepository, MoneySlotRepository moneySlotRepository) {
        this.recipePriceRepository = recipePriceRepository;
        this.moneySlotRepository = moneySlotRepository;
    }

    @EventListener
    public void handle(AutomatonCreatedEvent event) {
        this.getOrCreate(event.getAutomatonId());
    }

    @EventListener
    public void handle(AutomatonOffEvent event) {
        blockSlotWithAutomatonId(true, event.getAutomatonId());
    }

    @EventListener
    public void handle(AutomatonOnEvent event) {
        blockSlotWithAutomatonId(false, event.getAutomatonId());
    }

    @EventListener
    public void handle(MixingStartedEvent event) {
        blockSlotWithAutomatonId(true, event.getAutomatonId());
    }

    @EventListener
    public void handle(MixingFinishedEvent event) {
        blockSlotWithAutomatonId(false, event.getAutomatonId());
    }

    @Async
    @EventListener
    public void handle(RecipeSelectedEvent event) {
        RecipePrice selectedPrice = this.recipePriceRepository
                .findRecipePriceByRecipeId(event.getRecipeId());
        MoneySlot moneySlot = this.getOrCreate(event.getAutomatonId());
        moneySlot.didSelectRecipeWithPrice(selectedPrice);
        this.moneySlotRepository.save(moneySlot);
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent event) {
        this.moneySlotRepository.deleteAllById(event.getAutomatonIds());
    }

    public void insertCoin(Long automatonId, CoinType coinType) {
        this.moneySlotRepository.findById(automatonId)
                .ifPresent(moneySlot -> {
                    if (!moneySlot.isWaitingForCoins()) {
                        return;
                    }
                    // coin was inserted
                    RecipePrice price = this.recipePriceRepository.findRecipePriceById(moneySlot.getSelectedPriceId());
                    moneySlot.insertCoin(coinType, price);
                    this.moneySlotRepository.save(moneySlot);
                    // check if price is fully payed
                    if (price.getAmountDue(moneySlot.getInsertedAmountYet()) <= 0) {
                        moneySlot.priceIsPayedAndReset(price);
                        this.moneySlotRepository.save(moneySlot);
                    }
                });
    }

    private MoneySlot getOrCreate(Long automatonId) {
        Optional<MoneySlot> moneySlot = moneySlotRepository.findById(automatonId);
        if (moneySlot.isPresent()) {
            return moneySlot.get();
        }
        MoneySlot newMoneySlot = new MoneySlot();
        newMoneySlot.setAutomatonId(automatonId);
        newMoneySlot.setWaitingForCoins(true);
        return newMoneySlot;
    }

    private void blockSlotWithAutomatonId(boolean willBeBlocked, Long automatonId) {
        this.moneySlotRepository.findById(automatonId)
                .ifPresent(moneySlot -> {
                    moneySlot.setWaitingForCoins(!willBeBlocked);
                });
    }
}
