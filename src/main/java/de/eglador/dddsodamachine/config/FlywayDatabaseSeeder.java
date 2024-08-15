package de.eglador.dddsodamachine.config;

import de.eglador.dddsodamachine.recipe.domain.IngredientType;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

import java.sql.SQLException;
import java.util.UUID;

public class FlywayDatabaseSeeder implements Callback {

    @Override
    public boolean supports(Event event, Context context) {
        return event.name().equals(Event.AFTER_MIGRATE.name());
    }

    @Override
    public void handle(Event event, Context context) {
        try(var statement = context.getConnection().createStatement()) {

            var insertIngredientStatement = "insert into ingredient (type,volume_ml,id) values (%s,%s,'%s')";
            var insertRecipeStatement = "insert into recipe (name,id) values ('%s','%s')";
            var insertRecipeIngredientsStatement = "insert into recipe_ingredients (recipe_id,ingredients_id) values ('%s','%s')";
            var insertRecipePriceStatement = "insert into recipe_price (id,recipe_id,price_in_cent) values ('%s','%s',%s)";

            UUID ingredientId = UUID.randomUUID();
            statement.execute(insertIngredientStatement.formatted(
                    IngredientType.COFFEE.ordinal(),
                    500, ingredientId
            ));
            UUID recipeId = UUID.randomUUID();
            statement.execute(insertRecipeStatement.formatted(
                    "Coffee black",
                    recipeId
            ));
            statement.execute(insertRecipeIngredientsStatement.formatted(
                    recipeId, ingredientId
            ));
            UUID recipePriceId = UUID.randomUUID();
            statement.execute(insertRecipePriceStatement.formatted(
                    recipePriceId, recipeId, 200
            ));

            ingredientId = UUID.randomUUID();
            statement.execute(insertIngredientStatement.formatted(
                    IngredientType.COFFEE.ordinal(),
                    400, ingredientId
            ));
            UUID ingredientId2 = UUID.randomUUID();
            statement.execute(insertIngredientStatement.formatted(
                    IngredientType.MILK.ordinal(),
                    100, ingredientId2
            ));
            recipeId = UUID.randomUUID();
            statement.execute(insertRecipeStatement.formatted(
                    "Coffee white",
                    recipeId
            ));
            statement.execute(insertRecipeIngredientsStatement.formatted(
                    recipeId, ingredientId
            ));
            statement.execute(insertRecipeIngredientsStatement.formatted(
                    recipeId, ingredientId2
            ));
            recipePriceId = UUID.randomUUID();
            statement.execute(insertRecipePriceStatement.formatted(
                    recipePriceId, recipeId, 250
            ));

            ingredientId = UUID.randomUUID();
            statement.execute(insertIngredientStatement.formatted(
                    IngredientType.MILK.ordinal(),
                    500, ingredientId
            ));
            recipeId = UUID.randomUUID();
            statement.execute(insertRecipeStatement.formatted(
                    "Milk pure",
                    recipeId
            ));
            statement.execute(insertRecipeIngredientsStatement.formatted(
                    recipeId, ingredientId
            ));
            recipePriceId = UUID.randomUUID();
            statement.execute(insertRecipePriceStatement.formatted(
                    recipePriceId, recipeId, 280
            ));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public String getCallbackName() {
        return FlywayDatabaseSeeder.class.getName();
    }
}