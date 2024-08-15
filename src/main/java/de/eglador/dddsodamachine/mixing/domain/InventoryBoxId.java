package de.eglador.dddsodamachine.mixing.domain;

import de.eglador.dddsodamachine.recipe.domain.IngredientType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBoxId {
    private Long mixerId;
    private IngredientType ingredientType;
}
