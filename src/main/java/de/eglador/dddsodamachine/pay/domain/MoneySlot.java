package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.price.domain.RecipePrice;
import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MoneySlot extends AbstractAggregateRoot<MoneySlot> {
    @Id
    private Long automatonId;

    private RecipePriceId selectedPriceId;

    private boolean isWaitingForCoins;

    @ElementCollection(targetClass = CoinType.class)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<CoinType> payedCoinsYet;

    private int insertedAmountYet;

    public void insertCoin(CoinType coin, RecipePrice price) {
        if (!this.isWaitingForCoins()) {
            return;
        }
        this.setInsertedAmountYet(
                this.getInsertedAmountYet() + coin.getValue());
        List<CoinType> coins = this.getPayedCoinsYet();
        coins.add(coin);
        this.setPayedCoinsYet(coins);
        int amountDueInCent = price.getAmountDue(this.getInsertedAmountYet());
        if (amountDueInCent <= 0) {
            this.setWaitingForCoins(false);
        }
        registerEvent(new CoinInsertedEvent(this,
                automatonId, this.getSelectedPriceId(),
                price.getPriceInCent(), amountDueInCent, coin));
    }

    public void priceIsPayedAndReset(RecipePrice price) {
        List<CoinType> payed = List.copyOf(this.getPayedCoinsYet());
        int insertedAmount = this.getInsertedAmountYet();
        this.setWaitingForCoins(false);
        this.setInsertedAmountYet(0);
        this.setPayedCoinsYet(new ArrayList<>());
        this.setSelectedPriceId(null);
        registerEvent(new CoinInsertionFinishedEvent(this, this.automatonId,
                price.getId(), price.getPriceInCent(), payed,
                Math.abs(price.getAmountDue(insertedAmount))));
    }

    public void didSelectRecipeWithPrice(RecipePrice selectedPrice) {
        if (!this.isWaitingForCoins()) {
            return;
        }
        if (this.getInsertedAmountYet() > 0) {
            registerEvent(new CoinsInsertionCanceledEvent(
                    this, automatonId));
        }
        this.setInsertedAmountYet(0);
        this.setPayedCoinsYet(new ArrayList<>());
        this.setSelectedPriceId(selectedPrice.getId());
        this.setWaitingForCoins(true);
        registerEvent(new CoinInsertionStartEvent(
                this, automatonId,
                selectedPrice.getId(), selectedPrice.getPriceInCent()));
    }
}
