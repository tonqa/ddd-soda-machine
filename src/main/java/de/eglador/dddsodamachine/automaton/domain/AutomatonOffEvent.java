package de.eglador.dddsodamachine.automaton.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AutomatonOffEvent extends ApplicationEvent {
    private final Long automatonId;

    public AutomatonOffEvent(Object source, Long automatonId) {
        super(source);
        this.automatonId = automatonId;
    }
}
