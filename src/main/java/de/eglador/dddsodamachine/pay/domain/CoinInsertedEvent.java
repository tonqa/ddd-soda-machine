package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CoinInsertedEvent extends ApplicationEvent {
    private final Long automatonId;
    private final RecipePriceId recipePriceId;
    private final int price;
    private final int amountDue;
    private final CoinType coinType;
    public CoinInsertedEvent(Object source, Long automatonId, RecipePriceId recipePriceId, int price, int amountDue, CoinType coinType) {
        super(source);
        this.automatonId = automatonId;
        this.recipePriceId = recipePriceId;
        this.price = price;
        this.amountDue = amountDue;
        this.coinType = coinType;
    }
}
