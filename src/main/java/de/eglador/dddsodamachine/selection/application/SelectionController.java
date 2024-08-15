package de.eglador.dddsodamachine.selection.application;

import de.eglador.dddsodamachine.selection.domain.SelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/select")
@RequiredArgsConstructor
public class SelectionController {

    private final SelectionService selectionService;

    @PostMapping("/{selection}")
    public boolean select(@CookieValue(value = "automaton-id") Long automatonId,
                          @PathVariable String selection) {
        try {
            int recipeChoice = Integer.parseInt(selection);
            return this.selectionService.selectRecipe(automatonId, recipeChoice);
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
