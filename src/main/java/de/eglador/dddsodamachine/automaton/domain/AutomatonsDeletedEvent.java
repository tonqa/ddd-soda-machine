package de.eglador.dddsodamachine.automaton.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class AutomatonsDeletedEvent extends ApplicationEvent {
    private final List<Long> automatonIds;

    public AutomatonsDeletedEvent(Object source, List<Long> automatonIds) {
        super(source);
        this.automatonIds = automatonIds;
    }
}
