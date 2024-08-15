package de.eglador.dddsodamachine.price.domain;

import de.eglador.dddsodamachine.recipe.domain.RecipeId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RecipePrice extends AbstractAggregateRoot<RecipePrice> {

    @EmbeddedId
    private RecipePriceId id;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "recipe_id"))
    private RecipeId recipeId;
    private int priceInCent;

    public int getAmountDue(int insertedAmountYet) {
        return this.getPriceInCent() - insertedAmountYet;
    }

}
