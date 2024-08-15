package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.price.domain.RecipePriceId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CoinInsertionStartEvent extends ApplicationEvent {
    private final Long automatonId;
    private final RecipePriceId recipePriceId;
    private final int price;
    public CoinInsertionStartEvent(Object source, Long automatonId, RecipePriceId recipePriceId, int price) {
        super(source);
        this.automatonId = automatonId;
        this.recipePriceId = recipePriceId;
        this.price = price;
    }
}
