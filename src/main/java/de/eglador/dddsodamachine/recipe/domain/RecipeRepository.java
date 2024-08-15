package de.eglador.dddsodamachine.recipe.domain;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, RecipeId> {
}
