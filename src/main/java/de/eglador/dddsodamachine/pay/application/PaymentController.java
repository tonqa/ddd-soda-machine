package de.eglador.dddsodamachine.pay.application;

import de.eglador.dddsodamachine.pay.domain.CoinType;
import de.eglador.dddsodamachine.pay.domain.MoneySlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final MoneySlotService moneySlotService;

    @PostMapping("/{coin}")
    public boolean inserted(@CookieValue(value = "automaton-id") Long automatonId,
                            @PathVariable String coin) {
        try {
            CoinType coinType = CoinType.valueOf(coin);
            moneySlotService.insertCoin(automatonId, coinType);
        } catch (IllegalArgumentException exc) {
            return false;
        }
        return true;
    }

}
