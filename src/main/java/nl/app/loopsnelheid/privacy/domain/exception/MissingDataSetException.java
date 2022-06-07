package nl.app.loopsnelheid.privacy.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MissingDataSetException extends RuntimeException
{
    public MissingDataSetException(String message)
    {
        super(message);
    }
}
