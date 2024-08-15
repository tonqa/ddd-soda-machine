package de.eglador.dddsodamachine.pay.domain;

import de.eglador.dddsodamachine.price.domain.RecipePrice;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Register extends AbstractAggregateRoot<Register> {
    @Id
    private Long automatonId;
    @ElementCollection(targetClass = CoinCompartment.class)
    private List<CoinCompartment> compartments;

    public boolean allCompartmentsHaveCapacity(RecipePrice recipePrice) {
        return this.getCompartments().stream()
                .allMatch(compartment -> {
                    int leftCoinAmount = compartment.getMaximumCount()
                            - compartment.getCurrentCount();
                    int leftAmountInCent = leftCoinAmount
                            * compartment.getCoinType().getValue();
                    return leftAmountInCent >= recipePrice.getPriceInCent();
                });
    }

    public void addCoinsAndCashback(List<CoinType> payed, int overPayed) {
        payed.forEach(coinType -> this.getCompartments().stream()
                .filter(coinCompartment -> coinType.equals(coinCompartment.getCoinType()))
                .forEach(CoinCompartment::addOne));
        if (overPayed > 0) {
            registerEvent(new CoinsCashbackEvent(this, overPayed));
        }
    }
}
