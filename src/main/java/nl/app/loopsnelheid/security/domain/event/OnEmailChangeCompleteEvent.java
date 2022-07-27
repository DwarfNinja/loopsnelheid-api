package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnEmailChangeCompleteEvent extends ApplicationEvent
{
    private final User user;

    public OnEmailChangeCompleteEvent(User user)
    {
        super(user);

        this.user = user;
    }
}
