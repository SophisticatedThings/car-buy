package artem.strelcov.auth;

import artem.strelcov.user.User;
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
    private String firstname;
    private String lastname;

    public AuthenticationRequest(User user) {

        setEmail(user.getEmail());
        setPassword(user.getPassword());

    }
}
