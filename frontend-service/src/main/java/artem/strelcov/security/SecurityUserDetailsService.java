package artem.strelcov.security;

import artem.strelcov.model.User;
import artem.strelcov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> userResource = userRepository.findByName(name);
        if(userResource.isEmpty())
            throw new UsernameNotFoundException("Не найден пользователь = " + name);
        User user = userResource.get();
        return new org.springframework.security.core.userdetails.User(
                name,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}
