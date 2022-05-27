package nl.app.loopsnelheid.meassurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.data.DefaultMeasureRepository;
import nl.app.loopsnelheid.meassurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.security.domain.Sex;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultMeasureService
{
    private final DefaultMeasureRepository defaultMeasureRepository;

    public DefaultMeasure createDefaultMeasure(Sex sex, int age, double speed)
    {
        return new DefaultMeasure(sex, age, speed);
    }

    public void saveDefaultMeasure(Sex sex, int age, double speed)
    {
        defaultMeasureRepository.save(createDefaultMeasure(sex, age, speed));
    }

    public void saveAllDefaultMeasures(Set<DefaultMeasure> defaultMeasures)
    {
        defaultMeasureRepository.saveAll(defaultMeasures);
    }

    public List<DefaultMeasure> getDefaultMeasuresBySex(Sex sex)
    {
        return defaultMeasureRepository.findAllBySex(sex);
    }

    public DefaultMeasure getDefaultMeasureBySexAndAge(Sex sex, int age)
    {
        return defaultMeasureRepository.findBySexAndAge(sex, age)
                .orElseThrow(() -> new RuntimeException("Gegeven geslacht en leeftijd is geen match in de dataset"));
    }
}
