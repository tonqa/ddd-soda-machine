package de.eglador.dddsodamachine.recipe.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientComposite {
    private IngredientId id;
    private IngredientType type;
    private Integer volumeMl;
}
