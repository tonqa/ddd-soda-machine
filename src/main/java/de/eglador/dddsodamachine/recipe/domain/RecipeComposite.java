package de.eglador.dddsodamachine.recipe.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class RecipeComposite implements Serializable {

    private RecipeId recipeId;
    private String recipeName;
    private List<IngredientComposite> ingredients;

}
