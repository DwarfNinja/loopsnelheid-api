package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.ResetEmailVerification;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnEmailChangeEvent extends ApplicationEvent
{
    private final ResetEmailVerification resetEmailVerification;

    public OnEmailChangeEvent(ResetEmailVerification resetEmailVerification)
    {
        super(resetEmailVerification);

        this.resetEmailVerification = resetEmailVerification;
    }
}
