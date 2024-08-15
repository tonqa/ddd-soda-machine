package de.eglador.dddsodamachine.mixing.domain;

import de.eglador.dddsodamachine.recipe.domain.IngredientType;
import de.eglador.dddsodamachine.recipe.domain.RecipeComposite;
import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Mixer extends AbstractAggregateRoot<Mixer> {
    @Id
    private Long automatonId;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    private Set<InventoryBox> inventoryBoxes;
    private RecipeId selectedRecipe;

    public void initMixer(Long automatonId, RecipeId recipeId) {
        InventoryBox coffeeBox = new InventoryBox();
        coffeeBox.setId(new InventoryBoxId(automatonId, IngredientType.COFFEE));
        coffeeBox.setFilledMl(600);

        InventoryBox milkBox = new InventoryBox();
        milkBox.setId(new InventoryBoxId(automatonId, IngredientType.MILK));
        milkBox.setFilledMl(500);

        this.setAutomatonId(automatonId);
        this.setInventoryBoxes(Set.of(coffeeBox, milkBox));
        this.setSelectedRecipe(recipeId);
    }

    public boolean hasEnoughFillForRecipe(RecipeComposite recipe) {
        return recipe.getIngredients().stream()
                .map(ingredient -> this.getInventoryBoxes().stream()
                        .filter(inventoryBox -> inventoryBox.getId().getIngredientType().equals(ingredient.getType()))
                        .anyMatch(inventoryBox -> inventoryBox.hasEnough(ingredient.getVolumeMl())))
                .reduce((aBoolean, aBoolean2) -> aBoolean && aBoolean2)
                .orElse(false);
    }

    public List<Future<Boolean>> mixRecipe(RecipeComposite recipe) {
        this.registerEvent(new MixingStartedEvent(this, automatonId, recipe.getRecipeId()));
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            return recipe.getIngredients().stream()
                    .map(ingredient -> executorService.submit(() -> {
                        try {
                            Thread.sleep(1000L * ingredient.getVolumeMl() / 100);
                            return true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .toList();
        }
    }

    public void finishMixing(RecipeComposite recipe) {
        this.selectedRecipe = null;
        registerEvent(new MixingFinishedEvent(this, automatonId, recipe.getRecipeId()));
    }

}
