package nl.app.loopsnelheid.account.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.account.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationCompleteEvent(
            User user) {
        super(user);

        this.user = user;
    }
}
