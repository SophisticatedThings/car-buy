package artem.strelcov.exceptions.UserHandling;

public class NotUniqueUsernameException extends RuntimeException{
    public NotUniqueUsernameException(String message) {
        super(message);
    }
}
