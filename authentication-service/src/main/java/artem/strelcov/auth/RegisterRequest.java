package artem.strelcov.auth;

import artem.strelcov.user.Role;
import artem.strelcov.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public RegisterRequest(User user){
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
    }
}