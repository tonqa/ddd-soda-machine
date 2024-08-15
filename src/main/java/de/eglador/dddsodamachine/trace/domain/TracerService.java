package de.eglador.dddsodamachine.trace.domain;

import de.eglador.dddsodamachine.automaton.domain.AutomatonCreatedEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOffEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonOnEvent;
import de.eglador.dddsodamachine.automaton.domain.AutomatonsDeletedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingFinishedEvent;
import de.eglador.dddsodamachine.mixing.domain.MixingStartedEvent;
import de.eglador.dddsodamachine.pay.domain.*;
import de.eglador.dddsodamachine.selection.domain.RecipeSelectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TracerService {

    Logger LOG = LoggerFactory.getLogger(TracerService.class);

    private final boolean enableTracing = true;

    @EventListener
    public void handle(AutomatonCreatedEvent event) {
        if (enableTracing) {
            LOG.info("Automaton created");
        }
    }

    @EventListener
    public void handle(AutomatonOnEvent event) {
        if (enableTracing) {
            LOG.info("Automaton on");
        }
    }

    @EventListener
    public void handle(AutomatonOffEvent event) {
        if (enableTracing) {
            LOG.info("Automaton off");
        }
    }

    @EventListener
    public void handle(AutomatonsDeletedEvent event) {
        if (enableTracing) {
            LOG.info("Automatons deleted");
        }
    }

    @EventListener
    public void handle(RecipeSelectedEvent event) {
        if (enableTracing) {
            LOG.info("Recipe selected");
        }
    }

    @EventListener
    public void handle(CoinInsertionStartEvent event) {
        if (enableTracing) {
            LOG.info("Coin insertion started");
        }
    }

    @EventListener
    public void handle(CoinInsertedEvent event) {
        if (enableTracing) {
            LOG.info("Coin inserted");
        }
    }

    @EventListener
    public void handle(CoinInsertionFinishedEvent event) {
        if (enableTracing) {
            LOG.info("Coin insertion finished");
        }
    }

    @EventListener
    public void handle(CoinsInsertionCanceledEvent event) {
        if (enableTracing) {
            LOG.info("Coin insertion canceled");
        }
    }

    @EventListener
    public void handle(CoinsCashbackEvent event) {
        if (enableTracing) {
            LOG.info("Coin Cashback");
        }
    }

    @EventListener
    public void handle(MixingStartedEvent event) {
        if (enableTracing) {
            LOG.info("Mixing started");
        }
    }

    @EventListener
    public void handle(MixingFinishedEvent event) {
        if (enableTracing) {
            LOG.info("Mixing finished");
        }
    }

}
