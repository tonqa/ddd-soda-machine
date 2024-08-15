package de.eglador.dddsodamachine.automaton.application;

import de.eglador.dddsodamachine.automaton.domain.AutomatonComposite;

public interface AutomatonService {
    AutomatonComposite createAutomaton();
    AutomatonComposite findAutomatonById(Long id);
    void turnOn(Long automatonId);
    void turnOff(Long automatonId);
}
