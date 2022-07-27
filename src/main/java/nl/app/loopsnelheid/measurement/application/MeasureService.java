package nl.app.loopsnelheid.measurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.data.DefaultMeasureRepository;
import nl.app.loopsnelheid.measurement.data.MeasureRepository;
import nl.app.loopsnelheid.measurement.domain.Measure;
import nl.app.loopsnelheid.measurement.presentation.dto.MeasureDto;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MeasureService
{
    private final MeasureRepository measureRepository;
    private final DefaultMeasureRepository defaultMeasureRepository;

    public List<Measure> getAllMeasures()
    {
        return this.measureRepository.findAll();
    }

    public List<Measure> getMeasuresBetweenDatesByUser(LocalDateTime startDate, LocalDateTime endDate, Long userId)
    {
        return this.measureRepository.findAllBetweenDates(startDate, endDate, userId);
    }

    public List<Measure> getMeasuresBetweenDates(LocalDateTime startDate, LocalDateTime endDate)
    {
        return this.measureRepository.findAllBetweenDates(startDate, endDate);
    }

    public List<Measure> createManyMeasures(List<MeasureDto> dtos, User authenticatedUser)
    {
        List<Measure> measures = dtos.stream()
                .map(measureDto -> new Measure(measureDto.walkingSpeed, measureDto.registeredAt, authenticatedUser))
                .collect(Collectors.toList());

        this.measureRepository.saveAll(measures);

        return measures;
    }
}
