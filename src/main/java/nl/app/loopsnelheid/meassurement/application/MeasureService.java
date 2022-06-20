package nl.app.loopsnelheid.meassurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.data.DefaultMeasureRepository;
import nl.app.loopsnelheid.meassurement.data.MeasureRepository;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDto;
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

    public List<Measure> createManyMeasures(List<MeasureDto> measureDtos, User authenticatedUser)
    {
        List<Measure> measures = measureDtos.stream()
                .map(measureDto -> new Measure(measureDto.walkingSpeed, measureDto.registeredAt, authenticatedUser))
                .collect(Collectors.toList());

        this.measureRepository.saveAll(measures);

        return measures;
    }
}
