package de.eglador.dddsodamachine.websocket.application;

public interface WebSocketHandler {
    void sendMessage(Long automatonId, String message);
}
