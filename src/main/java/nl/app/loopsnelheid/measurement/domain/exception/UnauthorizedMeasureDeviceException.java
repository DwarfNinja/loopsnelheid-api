package nl.app.loopsnelheid.measurement.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedMeasureDeviceException extends RuntimeException
{
    public UnauthorizedMeasureDeviceException()
    {
        super("Gegeven apparaat is geen meetapparaat.");
    }
}
