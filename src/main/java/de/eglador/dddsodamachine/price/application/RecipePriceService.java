package de.eglador.dddsodamachine.price.application;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import de.eglador.dddsodamachine.price.domain.RecipePriceId;

public interface RecipePriceService {
    RecipePriceId recipePriceIdByRecipe(RecipeId recipeId);
}
