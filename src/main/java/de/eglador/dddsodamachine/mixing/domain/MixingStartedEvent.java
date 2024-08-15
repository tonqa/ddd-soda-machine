package de.eglador.dddsodamachine.mixing.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MixingStartedEvent extends ApplicationEvent {
    private final Long automatonId;
    private final RecipeId recipeId;

    public MixingStartedEvent(Object source, Long automatonId, RecipeId recipeId) {
        super(source);
        this.automatonId = automatonId;
        this.recipeId = recipeId;
    }
}
