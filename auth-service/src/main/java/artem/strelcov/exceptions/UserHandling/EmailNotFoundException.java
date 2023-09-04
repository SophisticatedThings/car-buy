package artem.strelcov.exceptions.UserHandling;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }

}
