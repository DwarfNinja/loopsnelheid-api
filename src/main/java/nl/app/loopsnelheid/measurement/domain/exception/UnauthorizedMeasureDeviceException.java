package nl.app.loopsnelheid.measurement.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedMeasureDeviceException extends RuntimeException
{
    public UnauthorizedMeasureDeviceException()
    {
        super("Het huidige apparaat is geen meetapparaat.");
    }
}
