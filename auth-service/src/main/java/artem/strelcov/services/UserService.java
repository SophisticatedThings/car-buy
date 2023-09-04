package artem.strelcov.services;

import artem.strelcov.auth.AuthenticationRequest;
import artem.strelcov.auth.AuthenticationResponse;
import artem.strelcov.auth.RegisterRequest;
import artem.strelcov.auth.RegisterResponse;
import artem.strelcov.exceptions.UserHandling.EmailNotFoundException;
import artem.strelcov.exceptions.UserHandling.NotUniqueEmailException;
import artem.strelcov.model.Role;
import artem.strelcov.model.User;
import artem.strelcov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> userCheck = userRepository.findByEmail(request.getEmail());
        if(userCheck.isPresent()) {
            throw new NotUniqueEmailException("username занят, пожалуйста, введите другой");
        }
        var user = artem.strelcov.model.User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return RegisterResponse.builder()
                .responseMessage("Регистрация прошла успешно, теперь Вы можете залогиниться")
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if(optionalUser.isEmpty()) {
            throw new EmailNotFoundException("Указанный Вами email не существует");
        }
        User user = optionalUser.get();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
