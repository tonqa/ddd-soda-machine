package de.eglador.dddsodamachine.recipe.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class RecipeId implements Serializable {
    private UUID id;
}
