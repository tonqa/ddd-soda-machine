package de.eglador.dddsodamachine.display.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class DisplayMessage extends AbstractAggregateRoot<DisplayMessage> {
    @EmbeddedId
    private DisplayMessageId id;
    private String message;
}
