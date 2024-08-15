package de.eglador.dddsodamachine.websocket.domain;

import de.eglador.dddsodamachine.websocket.application.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
public class WebSocketHandlerImpl implements WebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(Long automatonId, String message) {
        this.messagingTemplate.convertAndSend("/topic/messages/" + automatonId, new WebSocketMessage(message));
    }

}
