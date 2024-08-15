package de.eglador.dddsodamachine.pay.application;

import de.eglador.dddsodamachine.price.domain.RecipePriceId;

public interface RegisterService {
    boolean hasEnoughSpace(Long automatonId, RecipePriceId recipePriceId);
}
