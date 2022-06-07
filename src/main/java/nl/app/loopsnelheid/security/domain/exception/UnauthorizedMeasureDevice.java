package nl.app.loopsnelheid.security.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedMeasureDevice extends RuntimeException
{
    public UnauthorizedMeasureDevice()
    {
        super("Gegeven apparaat is geen meetapparaat.");
    }
}
