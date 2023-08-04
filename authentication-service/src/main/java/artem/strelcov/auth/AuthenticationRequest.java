package artem.strelcov.auth;

import artem.strelcov.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(User user) {

        setEmail(user.getEmail());
        setPassword(user.getPassword());

    }
}
