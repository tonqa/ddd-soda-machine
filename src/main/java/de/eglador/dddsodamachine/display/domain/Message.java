package de.eglador.dddsodamachine.display.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends AbstractAggregateRoot<Message> {
    @EmbeddedId
    private MessageId id;
    private String message;
}
