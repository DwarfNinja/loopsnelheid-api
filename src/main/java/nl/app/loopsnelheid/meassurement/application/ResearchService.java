package nl.app.loopsnelheid.meassurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.domain.ResearchStatistic;
import nl.app.loopsnelheid.meassurement.domain.ResearchStatisticAge;
import nl.app.loopsnelheid.meassurement.domain.event.OnResearchDataRequestCompleteEvent;
import nl.app.loopsnelheid.privacy.application.handler.ArchiveHandler;
import nl.app.loopsnelheid.privacy.application.handler.FileHandler;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResearchService
{
    private final ApplicationEventPublisher eventPublisher;

    public ResearchStatistic getResearchStatisticBetweenAge(int from, int to, List<User> researchCandidates)
    {
        Map<Integer, ResearchStatisticAge> statisticPerAge = new HashMap<>();

        for (int i = from; i < to; i++)
        {
            int age = i;
            List<User> candidatesByAge = researchCandidates.stream()
                    .filter(user -> user.getAge() == age)
                    .collect(Collectors.toList());

            double averageSpeedByMale = candidatesByAge.stream()
                    .filter(user -> user.getSex().equals(Sex.MALE))
                    .map(user -> user.getMeasures().stream()
                            .mapToDouble(Measure::getWalkingSpeed).average().orElse(0)
                    ).mapToDouble(d -> d).average().orElse(0.0);

            double averageSpeedByFemale = candidatesByAge.stream()
                    .filter(user -> user.getSex().equals(Sex.FEMALE))
                    .map(user -> user.getMeasures().stream()
                            .mapToDouble(Measure::getWalkingSpeed).average().orElse(0)
                    ).mapToDouble(d -> d).average().orElse(0.0);

            statisticPerAge.put(
                    age,
                    new ResearchStatisticAge(averageSpeedByMale, averageSpeedByFemale)
            );
        }

        return new ResearchStatistic(statisticPerAge);
    }

    @Transactional
    public void handleRequest(ResearchStatistic researchStatistic)
    {
        FileHandler fileHandler = new FileHandler(researchStatistic);
        fileHandler.handle();
        File file = fileHandler.getFile();

        ArchiveHandler archiveHandler = new ArchiveHandler(file);
        archiveHandler.handle();

        researchStatistic.setPath(archiveHandler.getPath());
        fileHandler.removeFile();

        eventPublisher.publishEvent(new OnResearchDataRequestCompleteEvent(researchStatistic));
    }
}
