package de.eglador.dddsodamachine.mixing.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MixingFinishedEvent extends ApplicationEvent {
    private final Long automatonId;
    private final RecipeId recipeId;

    public MixingFinishedEvent(Object source, Long automatonId, RecipeId recipeId) {
        super(source);
        this.automatonId = automatonId;
        this.recipeId = recipeId;
    }
}
