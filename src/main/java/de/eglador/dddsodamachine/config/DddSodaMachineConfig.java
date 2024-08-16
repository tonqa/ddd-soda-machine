package de.eglador.dddsodamachine.config;

import de.eglador.dddsodamachine.automaton.application.AutomatonService;
import de.eglador.dddsodamachine.automaton.domain.AutomatonConverter;
import de.eglador.dddsodamachine.automaton.domain.AutomatonRepository;
import de.eglador.dddsodamachine.automaton.domain.AutomatonServiceImpl;
import de.eglador.dddsodamachine.mixing.application.MixerService;
import de.eglador.dddsodamachine.mixing.domain.*;
import de.eglador.dddsodamachine.pay.domain.RegisterFactory;
import de.eglador.dddsodamachine.pay.domain.RegisterRepository;
import de.eglador.dddsodamachine.price.application.RecipePriceService;
import de.eglador.dddsodamachine.pay.application.RegisterService;
import de.eglador.dddsodamachine.price.domain.RecipePriceRepository;
import de.eglador.dddsodamachine.price.domain.RecipePriceServiceImpl;
import de.eglador.dddsodamachine.pay.domain.RegisterServiceImpl;
import de.eglador.dddsodamachine.recipe.application.RecipeService;
import de.eglador.dddsodamachine.recipe.domain.RecipeConverter;
import de.eglador.dddsodamachine.recipe.domain.RecipeRepository;
import de.eglador.dddsodamachine.recipe.domain.RecipeServiceImpl;
import de.eglador.dddsodamachine.websocket.application.WebSocketPort;
import de.eglador.dddsodamachine.websocket.adapters.WebSocketAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class DddSodaMachineConfig {

    @Bean
    public AutomatonService automatonService(
            ApplicationEventPublisher eventPublisher,
            AutomatonRepository automatonRepository,
            AutomatonConverter automatonConverter) {
        return new AutomatonServiceImpl(eventPublisher, automatonRepository,
                automatonConverter);
    }

    @Bean
    public RecipeService recipeService(
            RecipeRepository recipeRepository,
            RecipeConverter recipeConverter) {
        return new RecipeServiceImpl(recipeRepository, recipeConverter);
    }

    @Bean
    public MixerService mixerService(
            MixerRepository mixerRepository,
            RecipeService recipeService) {
        return new MixerServiceImpl(mixerRepository, recipeService);
    }

    @Bean
    public RecipePriceService recipePriceService(
            RecipePriceRepository recipePriceRepository) {
        return new RecipePriceServiceImpl(recipePriceRepository);
    }

    @Bean
    public RegisterService registerService(
            RecipePriceRepository recipePriceRepository,
            RegisterRepository registerRepository,
            RegisterFactory registerFactory) {
        return new RegisterServiceImpl(recipePriceRepository,
                registerRepository, registerFactory);
    }

    @Bean
    public WebSocketPort webSocketHandler(SimpMessagingTemplate messagingTemplate) {
        return new WebSocketAdapter(messagingTemplate);
    }
 }
