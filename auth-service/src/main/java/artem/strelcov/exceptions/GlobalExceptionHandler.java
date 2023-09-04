package artem.strelcov.exceptions;

import artem.strelcov.exceptions.UserHandling.EmailNotFoundException;
import artem.strelcov.exceptions.UserHandling.NotUniqueEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleNotUniqueEmailException(
            NotUniqueEmailException e){
        IncorrectData data = new IncorrectData();
        data.setInformation(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleEmailNotFoundException(
            EmailNotFoundException e){
        IncorrectData data = new IncorrectData();
        data.setInformation(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);

    }
}
