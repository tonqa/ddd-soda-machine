package de.eglador.dddsodamachine.websocket.adapters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketMessage {
    private String content;
}
