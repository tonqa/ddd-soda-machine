package de.eglador.dddsodamachine.price.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import de.eglador.dddsodamachine.price.application.RecipePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipePriceServiceImpl implements RecipePriceService {
    private final RecipePriceRepository recipePriceRepository;

    public RecipePriceId recipePriceIdByRecipe(RecipeId recipeId) {
        return this.recipePriceRepository
                .findRecipePriceByRecipeId(recipeId).getId();
    }

}
