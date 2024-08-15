package de.eglador.dddsodamachine.price.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class RecipePriceId implements Serializable {
    private UUID id;
}
