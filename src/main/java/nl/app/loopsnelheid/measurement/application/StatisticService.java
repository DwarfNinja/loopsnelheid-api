package nl.app.loopsnelheid.measurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.*;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class StatisticService
{
    private final MeasureService measureService;

    public MeasureStatistic getAverageMeasuresOfToday(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusHours(24);

        List<Measure> measuresToday = measureService.getMeasuresBetweenDatesByUser(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresToday, MeasureStatisticType.DAY);
    }

    public MeasureStatistic getAverageMeasuresOfCurrentWeek(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(7);

        List<Measure> measuresThisWeek = measureService.getMeasuresBetweenDatesByUser(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.WEEK);
    }

    public MeasureStatistic getAverageMeasuresOfCurrentMonth(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(31);

        List<Measure> measuresThisWeek = measureService.getMeasuresBetweenDatesByUser(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.MONTH);
    }
}
