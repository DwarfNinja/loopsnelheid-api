package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDeleteRequestProcessedEvent extends ApplicationEvent
{
    private final DeleteRequest deleteRequest;

    public OnDeleteRequestProcessedEvent(DeleteRequest deleteRequest)
    {
        super(deleteRequest);

        this.deleteRequest = deleteRequest;
    }
}
