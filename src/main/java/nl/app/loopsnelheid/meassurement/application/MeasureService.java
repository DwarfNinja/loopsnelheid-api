package nl.app.loopsnelheid.meassurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.data.MeasureRepository;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDTO;
import nl.app.loopsnelheid.security.application.RegisterService;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public List<Measure> getAllMeasures()
    {
        return this.measureRepository.findAll();
    }

    public List<Measure> getMeasuresBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Long userId)
    {
        return this.measureRepository.findAllBetweenDates(startDate, endDate, userId);
    }

    public List<Measure> createManyMeasures(List<MeasureDTO> measureDTOS, User authenticatedUser)
    {
        List<Measure> measures = measureDTOS.stream()
                .map(measureDTO -> new Measure(measureDTO.walkingSpeed, measureDTO.registeredAt, authenticatedUser))
                .collect(Collectors.toList());

        this.measureRepository.saveAll(measures);

        return measures;
    }
}
