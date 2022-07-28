package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResetPasswordEvent extends ApplicationEvent
{
    private final ResetPasswordVerification resetPasswordVerification;

    public OnResetPasswordEvent(ResetPasswordVerification resetPasswordVerification)
    {
        super(resetPasswordVerification);

        this.resetPasswordVerification = resetPasswordVerification;
    }
}
