package de.eglador.dddsodamachine.automaton.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Automaton extends AbstractAggregateRoot<Automaton> {

    @Id
    @GeneratedValue
    private Long id;

    private Instant expiryDate;

    @Enumerated(EnumType.ORDINAL)
    private AutomatonState state;

    public void initAutomaton() {
        this.setState(AutomatonState.OFF);
        this.setExpiryDate(Instant.now().plus(60, ChronoUnit.MINUTES));
    }

    public void setOn() {
        this.setState(AutomatonState.ON);
        registerEvent(new AutomatonOnEvent(this, id));
    }

    public void setOff() {
        this.setState(AutomatonState.OFF);
        registerEvent(new AutomatonOffEvent(this, id));
    }

}
