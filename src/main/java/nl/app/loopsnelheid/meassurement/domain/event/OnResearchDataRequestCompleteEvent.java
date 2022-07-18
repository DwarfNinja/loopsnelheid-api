package nl.app.loopsnelheid.meassurement.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.meassurement.domain.ResearchData;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResearchDataRequestCompleteEvent extends ApplicationEvent
{
    private final ResearchData researchData;

    public OnResearchDataRequestCompleteEvent(ResearchData researchData) {
        super(researchData);

        this.researchData = researchData;
    }
}
