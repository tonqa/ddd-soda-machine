package de.eglador.dddsodamachine.automaton.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AutomatonComposite {
    private Long id;
    private Instant expiryDate;
    private AutomatonState state;
}
