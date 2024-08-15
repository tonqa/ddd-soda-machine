package de.eglador.dddsodamachine.selection.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionStateRepository extends CrudRepository<SelectionState, Long> {
}
