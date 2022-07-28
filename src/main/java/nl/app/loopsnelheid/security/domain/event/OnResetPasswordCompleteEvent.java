package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResetPasswordCompleteEvent extends ApplicationEvent
{
    private final ResetPasswordVerification resetPasswordVerification;
    private final String password;

    public OnResetPasswordCompleteEvent(ResetPasswordVerification resetPasswordVerification, String password)
    {
        super(resetPasswordVerification);

        this.resetPasswordVerification = resetPasswordVerification;
        this.password = password;
    }
}
