package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class OnLoginCompleteEvent extends ApplicationEvent
{
    private final UserDetails userDetails;
    public OnLoginCompleteEvent(UserDetails userDetails)
    {
        super(userDetails);

        this.userDetails = userDetails;

    }
}
