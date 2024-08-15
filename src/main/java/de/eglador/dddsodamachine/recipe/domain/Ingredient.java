package de.eglador.dddsodamachine.recipe.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Ingredient {
    @EmbeddedId
    private IngredientId id;
    private IngredientType type;
    private Integer volumeMl;
}
