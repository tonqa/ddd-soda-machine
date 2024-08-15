package de.eglador.dddsodamachine.recipe.domain;

import de.eglador.dddsodamachine.recipe.application.RecipeService;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }

    public Iterable<RecipeComposite> recipes() {
        return StreamSupport.stream(this.recipeRepository.findAll().spliterator(), false)
                .map(recipeConverter::fromRecipe)
                .collect(Collectors.toList());
    }

    public Optional<RecipeComposite> recipeById(RecipeId id) {
        return this.recipeRepository.findById(id)
                .map(recipeConverter::fromRecipe);
    }

}
