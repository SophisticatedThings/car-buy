package artem.strelcov.controllers;

import artem.strelcov.dto.DateContainer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @GetMapping
    public String getApplicationPage(Model model) {
        model.addAttribute("date", new DateContainer());
        return "/app/application";
    }
    @PostMapping("/process_variants")
    public String processVariants(@ModelAttribute("date") DateContainer dateContainer) {
        System.out.println(dateContainer.getDate());
        return "/app/application";
    }
}
