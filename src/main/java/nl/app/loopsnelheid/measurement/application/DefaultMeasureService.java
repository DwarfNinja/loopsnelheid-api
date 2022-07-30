package nl.app.loopsnelheid.measurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.data.DefaultMeasureRepository;
import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.privacy.domain.exception.MissingDataSetException;
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

    public List<DefaultMeasure> getDefaultMeasuresBySex(Sex sex)
    {
        return defaultMeasureRepository.findAllBySex(sex);
    }

    public DefaultMeasure getDefaultMeasureBySexAndAge(Sex sex, int age)
    {
        return defaultMeasureRepository.findBySexAndAge(sex, age)
                .orElseThrow(() -> new MissingDataSetException("Gegeven geslacht en leeftijd is geen match in onze dataset."));
    }

    public boolean isDefaultMeasuresStored()
    {
        return defaultMeasureRepository.count() == 160;
    }

    public void saveAllDefaultMeasures(Set<DefaultMeasure> defaultMeasures)
    {
        if (!isDefaultMeasuresStored())
        {
            defaultMeasureRepository.saveAll(defaultMeasures);
        }
    }
}
