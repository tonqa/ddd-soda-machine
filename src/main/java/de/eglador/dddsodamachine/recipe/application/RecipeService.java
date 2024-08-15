package de.eglador.dddsodamachine.recipe.application;

import de.eglador.dddsodamachine.recipe.domain.RecipeComposite;
import de.eglador.dddsodamachine.recipe.domain.RecipeId;

import java.util.Optional;

public interface RecipeService {

    Iterable<RecipeComposite> recipes();
    Optional<RecipeComposite> recipeById(RecipeId id);
}
