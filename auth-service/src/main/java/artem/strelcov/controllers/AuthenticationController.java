package artem.strelcov.controllers;

import artem.strelcov.auth.AuthenticationRequest;
import artem.strelcov.auth.AuthenticationResponse;
import artem.strelcov.auth.RegisterRequest;
import artem.strelcov.auth.RegisterResponse;
import artem.strelcov.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    @PostMapping("/registration")
    public RegisterResponse register(
            @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
    @PostMapping("/authentication")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return userService.authenticate(request);
    }
}
