package nl.app.loopsnelheid.meassurement.application;

import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatisticType;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class StatisticService
{
    private final MeasureService measureService;

    public StatisticService(MeasureService measureService)
    {
        this.measureService = measureService;
    }

    public MeasureStatistic getAverageMeasuresOfToday(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusHours(24);

        List<Measure> measuresToday = measureService.getMeasuresBetweenDates(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresToday, MeasureStatisticType.DAY);
    }

    public MeasureStatistic getAverageMeasuresOfCurrentWeek(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(7);

        List<Measure> measuresThisWeek = measureService.getMeasuresBetweenDates(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.WEEK);
    }

    public MeasureStatistic getAverageMeasuresOfCurrentMonth(User authenticatedUser)
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(31);

        List<Measure> measuresThisWeek = measureService.getMeasuresBetweenDates(endDate, startDate, authenticatedUser.getId());

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.MONTH);
    }
}
