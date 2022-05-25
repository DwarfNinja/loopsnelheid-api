package nl.app.loopsnelheid.meassurement.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.application.StatisticService;
import nl.app.loopsnelheid.meassurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureStatisticDTO;
import nl.app.loopsnelheid.security.application.RegisterService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatisticController
{
    private final StatisticService statisticService;
    private final UserService userService;

    @GetMapping("/today")
    public MeasureStatisticDTO getCurrentDalyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfToday(authenticatedUser);

        return new MeasureStatisticDTO(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }

    @GetMapping("/week")
    public MeasureStatisticDTO getCurrentWeeklyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentWeek(authenticatedUser);

        return new MeasureStatisticDTO(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }
    
    @GetMapping("/month")
    public MeasureStatisticDTO getCurrentMonthlyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentMonth(authenticatedUser);

        return new MeasureStatisticDTO(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }
}
