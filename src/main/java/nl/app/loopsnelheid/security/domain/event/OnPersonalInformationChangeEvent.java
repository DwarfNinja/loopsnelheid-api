package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnPersonalInformationChangeEvent extends ApplicationEvent
{
    private final User user;

    public OnPersonalInformationChangeEvent(User user)
    {
        super(user);

        this.user = user;
    }
}
