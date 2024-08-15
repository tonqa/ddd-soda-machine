package de.eglador.dddsodamachine.recipe.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Recipe extends AbstractAggregateRoot<Recipe> {
    @EmbeddedId
    private RecipeId id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Ingredient> ingredients;
}
