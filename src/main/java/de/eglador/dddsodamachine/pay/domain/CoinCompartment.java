package de.eglador.dddsodamachine.pay.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoinCompartment implements Serializable {

    private CoinType coinType;
    private int maximumCount;
    private int currentCount;

    public void addOne() {
        assert currentCount < maximumCount;
        currentCount += 1;
    }
}
