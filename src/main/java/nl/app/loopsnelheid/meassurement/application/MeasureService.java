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

    public List<Measure> createMany(List<MeasureDTO> measureDTOS)
    {
        List<Measure> measures = measureDTOS.stream()
                .map(measureDTO -> new Measure(measureDTO.getWalkingSpeed(), measureDTO.getRegisteredAt()))
                .collect(Collectors.toList());

        this.measureRepository.saveAll(measures);

        return measures;
    }
}
