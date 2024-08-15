package de.eglador.dddsodamachine.price.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import org.springframework.data.repository.CrudRepository;

public interface RecipePriceRepository extends CrudRepository<RecipePrice, RecipePriceId> {
    RecipePrice findRecipePriceByRecipeId(RecipeId recipeId);
    RecipePrice findRecipePriceById(RecipePriceId recipePriceId);
}
