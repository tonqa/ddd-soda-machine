package de.eglador.dddsodamachine.selection.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeComposite;
import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SelectionState extends AbstractAggregateRoot<SelectionState> {
    @Id
    private Long automatonId;
    private boolean recipeCanBeSelected;
    @ElementCollection(targetClass = RecipeId.class)
    private Map<Integer, RecipeId> choiceToRecipeId;

    public void initSelectionState(Iterable<RecipeComposite> recipes) {
        this.setRecipeCanBeSelected(true);
        HashMap<Integer, RecipeId> choiceToRecipeId = new HashMap<>();
        Iterator<RecipeComposite> iterator = recipes.iterator();
        IntStream.range(0, 6)
                .forEach(index -> {
                    if (iterator.hasNext()) {
                        RecipeComposite recipeComposite = iterator.next();
                        choiceToRecipeId.put(index, recipeComposite.getRecipeId());
                    }
                });
        this.setChoiceToRecipeId(choiceToRecipeId);
    }
}
