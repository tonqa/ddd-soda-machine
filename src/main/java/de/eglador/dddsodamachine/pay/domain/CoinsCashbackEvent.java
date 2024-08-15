package de.eglador.dddsodamachine.pay.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CoinsCashbackEvent extends ApplicationEvent {
    private final int amount;
    public CoinsCashbackEvent(Object source, int amount) {
        super(source);
        this.amount = amount;
    }
}
