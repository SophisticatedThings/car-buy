package artem.strelcov.auth;

import artem.strelcov.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @GetMapping
    public ModelAndView getLoginPage(Model model){
        model.addAttribute("user", new User());
        return new ModelAndView("login");
    }
    @GetMapping("/register")
    public ModelAndView getRegisterPage(Model model){
        model.addAttribute("user", new User());
        return new ModelAndView("register");
    }
    @PostMapping("/process_register")
    public ModelAndView register(User user) {
        RegisterRequest request = new RegisterRequest(user);
        service.register(request);
        return new ModelAndView("redirect:/api/v1/auth");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(User user) {
        AuthenticationRequest request = new AuthenticationRequest(user);
        service.authenticate(request);
    }
}