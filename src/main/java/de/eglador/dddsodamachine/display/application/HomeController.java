package de.eglador.dddsodamachine.display.application;

import de.eglador.dddsodamachine.automaton.application.AutomatonService;
import de.eglador.dddsodamachine.automaton.domain.AutomatonComposite;
import de.eglador.dddsodamachine.display.domain.DisplayService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    public static final String COOKIE_AUTOMATON_ID = "automaton-id";
    private final DisplayService displayService;
    private final AutomatonService automatonService;

    @GetMapping
    public String greeting() {
        return "home";
    }

    @ModelAttribute("display")
    public String display(@CookieValue(value = COOKIE_AUTOMATON_ID, required = false) Long automatonId,
                          HttpServletResponse response) {
        if (automatonId == null || automatonService.findAutomatonById(automatonId) == null) {
            AutomatonComposite automaton = automatonService.createAutomaton();
            Cookie cookie = new Cookie(COOKIE_AUTOMATON_ID, String.valueOf(automaton.getId()));
            Duration duration = Duration.between(
                    LocalDateTime.ofInstant(automaton.getExpiryDate(), ZoneId.systemDefault()),
                    LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            cookie.setMaxAge((int) duration.getSeconds());
            response.addCookie(cookie);
        }
        return this.displayService.display(automatonId);
    }

}
