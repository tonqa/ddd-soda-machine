package de.eglador.dddsodamachine.automaton.application;

import de.eglador.dddsodamachine.automaton.domain.AutomatonState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automaton")
@RequiredArgsConstructor
public class AutomatonController {

    private final AutomatonService automatonService;

    @GetMapping("/state")
    public boolean isAutomatonActivate(@CookieValue(value = "automaton-id") Long automatonId) {
        return this.automatonService.findAutomatonById(automatonId)
                .getState().equals(AutomatonState.ON);
    }

    @PostMapping("/{selection}")
    public boolean select(@CookieValue(value = "automaton-id") Long automatonId,
            @PathVariable String selection) {
        try {
            AutomatonState selected = AutomatonState.valueOf(selection);
            switch (selected) {
                case ON -> this.automatonService.turnOn(automatonId);
                case OFF -> this.automatonService.turnOff(automatonId);
            }
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

}
