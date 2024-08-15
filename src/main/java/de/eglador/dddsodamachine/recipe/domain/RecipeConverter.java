package de.eglador.dddsodamachine.recipe.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeConverter {

    private final IngredientConverter ingredientConverter;

    public RecipeComposite fromRecipe(Recipe recipe) {
        return RecipeComposite.builder()
                .recipeId(recipe.getId())
                .recipeName(recipe.getName())
                .ingredients(recipe.getIngredients().stream()
                        .map(ingredientConverter::fromIngredient)
                        .collect(Collectors.toList()))
                .build();
    }

}
