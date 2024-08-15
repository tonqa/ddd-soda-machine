package de.eglador.dddsodamachine.mixing.application;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;

public interface MixerService {
    boolean hasEnoughFill(Long automatonId, RecipeId recipeId);
}
