package de.eglador.dddsodamachine.recipe.domain;

import org.springframework.stereotype.Service;

@Service
public class IngredientConverter {

    public IngredientComposite fromIngredient(Ingredient ingredient) {
        return IngredientComposite.builder()
                .id(ingredient.getId())
                .type(ingredient.getType())
                .volumeMl(ingredient.getVolumeMl())
                .build();
    }

}
