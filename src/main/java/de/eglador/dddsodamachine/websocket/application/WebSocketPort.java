package de.eglador.dddsodamachine.websocket.application;

public interface WebSocketPort {
    void sendMessage(Long automatonId, String message);
}
