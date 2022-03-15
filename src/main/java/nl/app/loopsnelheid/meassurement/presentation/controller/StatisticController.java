package nl.app.loopsnelheid.meassurement.presentation.controller;

import nl.app.loopsnelheid.meassurement.application.StatisticService;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.meassurement.domain.exceptions.NoAvailableMeasuresException;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureStatisticDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/stats")
public class StatisticController
{
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService)
    {
        this.statisticService = statisticService;
    }

    @GetMapping("/week")
    public MeasureStatisticDTO getCurrentWeeklyAverage()
    {
        try
        {
            MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentWeek();

            return new MeasureStatisticDTO(
                    measureStatistic.getStartDate(),
                    measureStatistic.getEndDate(),
                    measureStatistic.getMeasureStatisticType().toString(),
                    measureStatistic.getAverageWalkingSpeed(),
                    measureStatistic.getAmountOfMeasures()
            );
        }
        catch (NoAvailableMeasuresException noAvailableMeasuresException)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, noAvailableMeasuresException.getMessage());
        }
    }

    @GetMapping("/month")
    public MeasureStatisticDTO getCurrentMonthlyAverage()
    {
        try
        {
            MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentMonth();

            return new MeasureStatisticDTO(
                    measureStatistic.getStartDate(),
                    measureStatistic.getEndDate(),
                    measureStatistic.getMeasureStatisticType().toString(),
                    measureStatistic.getAverageWalkingSpeed(),
                    measureStatistic.getAmountOfMeasures()
            );
        }
        catch (NoAvailableMeasuresException noAvailableMeasuresException)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, noAvailableMeasuresException.getMessage());
        }
    }
}
