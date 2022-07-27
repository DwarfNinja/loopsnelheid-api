package nl.app.loopsnelheid.security.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OldPasswordIncorrectException extends RuntimeException
{
    public OldPasswordIncorrectException(String message) {
        super(message);
    }
}
