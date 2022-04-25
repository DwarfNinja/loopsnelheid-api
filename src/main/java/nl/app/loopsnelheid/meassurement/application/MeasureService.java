package nl.app.loopsnelheid.meassurement.application;

import nl.app.loopsnelheid.meassurement.data.MeasureRepository;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MeasureService
{
    private final MeasureRepository measureRepository;

    public MeasureService(MeasureRepository measureRepository)
    {
        this.measureRepository = measureRepository;
    }

    public List<Measure> getAllMeasures()
    {
        return this.measureRepository.findAll();
    }

    public List<Measure> getMeasuresBetweenDates(LocalDateTime startDate, LocalDateTime endDate)
    {
        return this.measureRepository.findAllBetweenDates(startDate, endDate);
    }

    public List<Measure> createManyMeasures(List<MeasureDTO> measureDTOS)
    {
        List<Measure> measures = measureDTOS.stream()
                .map(measureDTO -> new Measure(measureDTO.walkingSpeed, measureDTO.registeredAt))
                .collect(Collectors.toList());

        this.measureRepository.saveAll(measures);

        return measures;
    }
}
