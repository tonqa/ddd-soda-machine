package de.eglador.dddsodamachine.automaton.domain;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface AutomatonRepository extends CrudRepository<Automaton, Long> {
    List<Automaton> getAllByExpiryDateBefore(Instant expiryDate);
    void deleteAllByIdIn(Collection<Long> ids);
}
