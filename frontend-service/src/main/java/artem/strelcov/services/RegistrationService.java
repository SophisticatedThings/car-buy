package artem.strelcov.services;

import artem.strelcov.model.User;
import artem.strelcov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    public RegistrationService(UserService userService,
                               PasswordEncoder passwordEncoder,
                               UserRepository userRepository){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Transactional
    public User saveRegisteredPerson(User registeredUser) {
        User user = new User();
        if (registeredUser != null) {
            user.setName(registeredUser.getName());
            user.setRole("USER");
            user.setFirstname(registeredUser.getFirstname());
            user.setLastname(registeredUser.getLastname());
            String password = registeredUser.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }
}
