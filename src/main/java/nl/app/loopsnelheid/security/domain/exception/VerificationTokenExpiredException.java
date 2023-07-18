package nl.app.loopsnelheid.security.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VerificationTokenExpiredException extends RuntimeException
{
    public VerificationTokenExpiredException()
    {
        super("Gegeven verifieer code is verlopen.");
    }
}
