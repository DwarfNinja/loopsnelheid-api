package nl.app.loopsnelheid.privacy.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnDataRequestCompleteEvent extends ApplicationEvent
{
    private final DataRequest dataRequest;

    public OnDataRequestCompleteEvent(DataRequest dataRequest) {
        super(dataRequest);

        this.dataRequest = dataRequest;
    }
}
