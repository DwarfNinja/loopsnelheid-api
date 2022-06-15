package nl.app.loopsnelheid.privacy.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataRequestNotFoundException extends RuntimeException
{
    public DataRequestNotFoundException(String message)
    {
        super(message);
    }
}
