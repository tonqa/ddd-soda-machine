package de.eglador.dddsodamachine.mixing.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class InventoryBox {
    @Id
    @EmbeddedId
    private InventoryBoxId id;
    private int filledMl;

    public boolean hasEnough(int volumeMl) {
        return this.getFilledMl() >= volumeMl;
    }
}
