package de.eglador.dddsodamachine.pay.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum CoinType implements Serializable {

    TEN(10),
    TWENTY(20),
    FIFTY(50),
    EURO(100);

    private final int value;

    CoinType(int value) {
        this.value = value;
    }
}
