package de.eglador.dddsodamachine.pay.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterFactory {
    public Register createRegister(Long automatonId) {
        Register register = new Register();
        register.setAutomatonId(automatonId);
        List<CoinCompartment> compartments = new ArrayList<>();

        CoinCompartment compartment1 = new CoinCompartment();
        compartment1.setCoinType(CoinType.TEN);
        compartment1.setMaximumCount(50);
        compartment1.setCurrentCount(10);
        compartments.add(compartment1);

        CoinCompartment compartment2 = new CoinCompartment();
        compartment2.setCoinType(CoinType.TWENTY);
        compartment2.setMaximumCount(50);
        compartment2.setCurrentCount(20);
        compartments.add(compartment2);

        CoinCompartment compartment3 = new CoinCompartment();
        compartment3.setCoinType(CoinType.FIFTY);
        compartment3.setMaximumCount(50);
        compartment3.setCurrentCount(15);
        compartments.add(compartment3);

        CoinCompartment compartment4 = new CoinCompartment();
        compartment4.setCoinType(CoinType.EURO);
        compartment4.setMaximumCount(50);
        compartment4.setCurrentCount(25);
        compartments.add(compartment4);

        register.setCompartments(compartments);
        return register;
    }
}
