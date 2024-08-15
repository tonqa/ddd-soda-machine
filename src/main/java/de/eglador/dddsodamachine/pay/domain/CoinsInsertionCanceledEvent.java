package de.eglador.dddsodamachine.pay.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CoinsInsertionCanceledEvent extends ApplicationEvent {
    private final Long automatonId;

    public CoinsInsertionCanceledEvent(Object source, Long automatonId) {
        super(source);
        this.automatonId = automatonId;
    }
}
