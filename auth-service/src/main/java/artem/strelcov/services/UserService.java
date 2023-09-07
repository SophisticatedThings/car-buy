package artem.strelcov.services;

import artem.strelcov.auth.AuthenticationRequest;
import artem.strelcov.auth.AuthenticationResponse;
import artem.strelcov.auth.RegisterRequest;
import artem.strelcov.auth.RegisterResponse;
import artem.strelcov.dto.UserDto;
import artem.strelcov.exceptions.UserHandling.EmailNotFoundException;
import artem.strelcov.exceptions.UserHandling.NotUniqueEmailException;
import artem.strelcov.model.Role;
import artem.strelcov.model.User;
import artem.strelcov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WebClient.Builder webClient;
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> userCheck = userRepository.findByEmail(request.getEmail());
        if(userCheck.isPresent()) {
            throw new NotUniqueEmailException("email занят, пожалуйста, введите другой");
        }

        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .city(request.getCity())
                .registeredAt(LocalDateTime.now())
                .rating(0)
                .build();

        var user = artem.strelcov.model.User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var jwtToken = jwtService.generateToken(user);

        webClient.build()
                .post()
                .uri( "http://localhost:8085/users")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(jwtToken))
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

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
