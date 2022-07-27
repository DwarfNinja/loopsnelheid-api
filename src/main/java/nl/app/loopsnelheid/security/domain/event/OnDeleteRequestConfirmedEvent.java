package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDeleteRequestConfirmedEvent extends ApplicationEvent
{
    private final DeleteRequest deleteRequest;

    public OnDeleteRequestConfirmedEvent(DeleteRequest deleteRequest)
    {
        super(deleteRequest);

        this.deleteRequest = deleteRequest;
    }
}
