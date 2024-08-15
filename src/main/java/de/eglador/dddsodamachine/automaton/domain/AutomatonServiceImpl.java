package de.eglador.dddsodamachine.automaton.domain;

import de.eglador.dddsodamachine.automaton.application.AutomatonService;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class AutomatonServiceImpl implements AutomatonService {

    private final ApplicationEventPublisher eventPublisher;
    private final AutomatonRepository automatonRepository;
    private final AutomatonConverter automatonConverter;

    public AutomatonServiceImpl(ApplicationEventPublisher eventPublisher, AutomatonRepository automatonRepository, AutomatonConverter automatonConverter) {
        this.eventPublisher = eventPublisher;
        this.automatonRepository = automatonRepository;
        this.automatonConverter = automatonConverter;
    }

    public AutomatonComposite createAutomaton() {
        this.removeExpiredAutomatons();
        Automaton automaton = new Automaton();
        automaton.initAutomaton();
        Automaton saved = this.automatonRepository.save(automaton);
        eventPublisher.publishEvent(new AutomatonCreatedEvent(this, saved.getId()));
        return this.automatonConverter.fromAutomaton(saved);
    }

    public AutomatonComposite findAutomatonById(Long id) {
        Optional<Automaton> automaton = this.automatonRepository.findById(id);
        return automaton.map(this.automatonConverter::fromAutomaton).orElse(null);
    }

    public void turnOn(Long automatonId) {
        this.automatonRepository.findById(automatonId)
                .ifPresent(automaton -> {
                    automaton.setOn();
                    this.automatonRepository.save(automaton);
                });
    }

    public void turnOff(Long automatonId) {
        this.automatonRepository.findById(automatonId)
                .ifPresent(automaton -> {
                    automaton.setOff();
                    this.automatonRepository.save(automaton);
                });
    }

    private void removeExpiredAutomatons() {
        List<Long> ids = this.automatonRepository.getAllByExpiryDateBefore(Instant.now()).stream()
                .map(Automaton::getId)
                .toList();
        this.automatonRepository.deleteAllByIdIn(ids);
        this.eventPublisher.publishEvent(new AutomatonsDeletedEvent(this, ids));
    }

}
