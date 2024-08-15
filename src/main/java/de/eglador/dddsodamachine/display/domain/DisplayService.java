package de.eglador.dddsodamachine.display.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonCreatedEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOffEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOnEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingFinishedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingStartedEvent;
import de.eglador.dddsodamachine.recipe.application.RecipeService;
import de.eglador.dddsodamachine.recipe.domain.RecipeComposite;
import de.eglador.dddsodamachine.pay.domain.CoinInsertedEvent;
import de.eglador.dddsodamachine.pay.domain.CoinInsertionStartEvent;
import de.eglador.dddsodamachine.pay.domain.CoinsInsertionCanceledEvent;
import de.eglador.dddsodamachine.websocket.application.WebSocketHandler;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.stream.IntStream;

@Service
public class DisplayService {

    private final RecipeService recipeService;
    private final MessageRepository messageRepository;
    private final WebSocketHandler webSocketHandler;

    public DisplayService(RecipeService recipeService, MessageRepository messageRepository, WebSocketHandler webSocketHandler) {
        this.recipeService = recipeService;
        this.messageRepository = messageRepository;
        this.webSocketHandler = webSocketHandler;
    }

    public String display(Long automatonId) {
        MessageId messageId = new MessageId();
        messageId.setAutomatonId(automatonId);
        return this.messageRepository.findById(messageId)
                .map(Message::getMessage)
                .orElse("Automaton is off");
    }

    @EventListener
    public void handle(AutomatonCreatedEvent event) {
        writeMessage(event.getAutomatonId(), "Automaton is off");
    }

    @EventListener
    public void handle(AutomatonOnEvent readyEvent) {
        showInitialMessage(readyEvent.getAutomatonId());
    }

    @EventListener
    public void handle(AutomatonOffEvent offEvent) {
        writeMessage(offEvent.getAutomatonId(), "Automaton is off");
    }

    @EventListener
    public void handle(CoinInsertionStartEvent event) {
        writeMessage(event.getAutomatonId(), "Please insert " + event.getPrice() + " Cent.");
    }

    @EventListener
    public void handle(CoinInsertedEvent event) {
        if (event.getAmountDue() > 0) {
            writeMessage(event.getAutomatonId(), "You must pay "
                    + event.getAmountDue() + " of " + event.getPrice());
        } else {
            writeMessage(event.getAutomatonId(),
                    "Thanks. Waiting for your product...");
        }
    }

    @EventListener
    public void handle(CoinsInsertionCanceledEvent event) {
        showInitialMessage(event.getAutomatonId());
    }

    @EventListener
    public void handle(MixingStartedEvent event) {
        writeMessage(event.getAutomatonId(), "Thanks. Started mixing product...");
    }

    @Async
    @EventListener
    public void handle(MixingFinishedEvent event) {
        writeMessage(event.getAutomatonId(), "Mixing was finished. Enjoy your drink");
        try {
            Thread.sleep(3000);
            this.showInitialMessage(event.getAutomatonId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent event) {
        this.messageRepository.deleteAllById(event.getAutomatonIds().stream()
                .map(id -> {
                    MessageId messageId = new MessageId();
                    messageId.setAutomatonId(id);
                    return messageId;
                }).toList());
    }

    private void showInitialMessage(Long automatonId) {
        StringBuilder builder = new StringBuilder()
                .append("Hello, Anett! Automaton was switched on.")
                .append("\n");
        Iterator<RecipeComposite> recipes = this.recipeService.recipes().iterator();
        IntStream.range(0, 6)
                .forEach(index -> {
                    if (recipes.hasNext()) {
                        RecipeComposite recipeComposite = recipes.next();
                        builder
                                .append(String.format("(%d) ", index))
                                .append(recipeComposite.getRecipeName())
                                .append("\n");
                    }
                });
        this.writeMessage(automatonId, builder
                .append("Please choose one.")
                .toString());
    }

    private void writeMessage(Long automatonId, String msg) {
        MessageId messageId = new MessageId();
        messageId.setAutomatonId(automatonId);
        this.messageRepository.findById(messageId)
                .ifPresentOrElse(message -> {
                    message.setMessage(msg);
                    this.messageRepository.saveAndFlush(message);
                }, () -> {
                    Message message = new Message();
                    message.setId(messageId);
                    message.setMessage(msg);
                    this.messageRepository.saveAndFlush(message);
                });
        webSocketHandler.sendMessage(automatonId, msg);
    }
}
