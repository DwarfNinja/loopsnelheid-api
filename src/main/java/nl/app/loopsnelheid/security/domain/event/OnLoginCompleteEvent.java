package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.Device;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnLoginCompleteEvent extends ApplicationEvent
{
    private final User user;
    private final Device device;

    public OnLoginCompleteEvent(User user, Device device)
    {
        super(user);

        this.user = user;
        this.device = device;
    }
}
