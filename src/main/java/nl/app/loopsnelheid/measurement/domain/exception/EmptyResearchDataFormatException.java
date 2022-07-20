package nl.app.loopsnelheid.measurement.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyResearchDataFormatException extends RuntimeException
{
    public EmptyResearchDataFormatException()
    {
        super("Je moet op z'n minst één formaat uitkiezen.");
    }
}
