package nl.app.loopsnelheid.security.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDeleteRequestRevokedEvent extends ApplicationEvent
{
    private final DeleteRequest deleteRequest;

    public OnDeleteRequestRevokedEvent(DeleteRequest deleteRequest)
    {
        super(deleteRequest);

        this.deleteRequest = deleteRequest;
    }
}
