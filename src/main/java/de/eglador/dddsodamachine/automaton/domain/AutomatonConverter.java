package de.eglador.dddsodamachine.automaton.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutomatonConverter {

    public AutomatonComposite fromAutomaton(Automaton automaton) {
        return AutomatonComposite.builder()
                .id(automaton.getId())
                .state(automaton.getState())
                .expiryDate(automaton.getExpiryDate())
                .build();
    }

}
