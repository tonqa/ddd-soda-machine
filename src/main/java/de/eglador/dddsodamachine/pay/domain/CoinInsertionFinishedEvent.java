package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class CoinInsertionFinishedEvent extends ApplicationEvent {
    private final Long automatonId;
    private final RecipePriceId recipePriceId;
    private final int price;
    private final List<CoinType> payedCoinsYet;
    private final int overPayed;

    public CoinInsertionFinishedEvent(Object source, Long automatonId, RecipePriceId recipePriceId, int price, List<CoinType> payedCoinsYet, int overPayed) {
        super(source);
        this.automatonId = automatonId;
        this.recipePriceId = recipePriceId;
        this.price = price;
        this.payedCoinsYet = payedCoinsYet;
        this.overPayed = overPayed;
    }
}
