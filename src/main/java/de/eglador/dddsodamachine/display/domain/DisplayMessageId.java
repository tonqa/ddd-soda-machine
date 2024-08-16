package de.eglador.dddsodamachine.display.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DisplayMessageId implements Serializable {
    Long automatonId;
}
