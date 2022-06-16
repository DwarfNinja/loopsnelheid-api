package nl.app.loopsnelheid.meassurement.domain.event;

import lombok.Getter;
import nl.app.loopsnelheid.meassurement.domain.ResearchStatistic;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnResearchDataRequestCompleteEvent extends ApplicationEvent
{
    private final ResearchStatistic researchStatistic;

    public OnResearchDataRequestCompleteEvent(ResearchStatistic researchStatistic) {
        super(researchStatistic);

        this.researchStatistic = researchStatistic;
    }
}
