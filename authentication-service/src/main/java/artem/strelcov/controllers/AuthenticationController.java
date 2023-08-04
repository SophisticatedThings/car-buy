package artem.strelcov.controllers;

import artem.strelcov.auth.AuthenticationRequest;
import artem.strelcov.services.RegistrationService;
import artem.strelcov.services.UserService;
import artem.strelcov.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;
    private final RegistrationService registrationService;
    @GetMapping("/login")
    public String getLoginPage(){

        return "auth/login";
    }
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        System.out.println("register");
        model.addAttribute("user", new User());
        return "auth/register";
    }
    //HttpServletRequest для реализации верификации почты
   @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        registrationService.saveRegisteredPerson(user);
        return "redirect:/auth/login";
    }
    /*@GetMapping("/verify")
    public ModelAndView verifyUser(@Param("code") String code){
        service.verify(code);
        return new ModelAndView("verify-success");
    } */
    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute("user")User user) {
        System.out.println("Method is allowed");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        AuthenticationRequest request = new AuthenticationRequest(user);
        return "redirect:/api/v1/testing";

    }
}