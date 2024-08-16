package de.eglador.dddsodamachine.display.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DisplayMessageRepository extends CrudRepository<DisplayMessage, DisplayMessageId>, JpaRepository<DisplayMessage, DisplayMessageId> {
}
