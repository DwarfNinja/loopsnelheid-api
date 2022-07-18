package nl.app.loopsnelheid.measurement.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResearchDataRequestCompleteEvent extends ApplicationEvent
{
    private final String path;
    private final ResearchData researchData;

    public OnResearchDataRequestCompleteEvent(String path, ResearchData researchData) {
        super(researchData);

        this.path = path;
        this.researchData = researchData;
    }
}
