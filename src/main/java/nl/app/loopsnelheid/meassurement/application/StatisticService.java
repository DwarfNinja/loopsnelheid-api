package nl.app.loopsnelheid.meassurement.application;

import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatisticType;
import nl.app.loopsnelheid.meassurement.domain.exceptions.NoAvailableMeasuresException;
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

    public MeasureStatistic getAverageMeasuresOfCurrentWeek() throws NoAvailableMeasuresException
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(7);

        List<Measure> measuresThisWeek = this.measureService.getMeasuresBetweenDates(endDate, startDate);

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.WEEK);
    }

    public MeasureStatistic getAverageMeasuresOfCurrentMonth() throws NoAvailableMeasuresException
    {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusDays(31);

        List<Measure> measuresThisWeek = this.measureService.getMeasuresBetweenDates(endDate, startDate);

        return new MeasureStatistic(startDate, endDate, measuresThisWeek, MeasureStatisticType.MONTH);
    }
}
