package de.eglador.dddsodamachine.websocket.adapters;

import de.eglador.dddsodamachine.websocket.application.WebSocketPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
public class WebSocketAdapter implements WebSocketPort {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(Long automatonId, String message) {
        this.messagingTemplate.convertAndSend("/topic/messages/" + automatonId, new WebSocketMessage(message));
    }

}
