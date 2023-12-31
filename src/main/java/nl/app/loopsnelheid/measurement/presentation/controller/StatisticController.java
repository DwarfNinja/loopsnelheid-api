package nl.app.loopsnelheid.measurement.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.DefaultMeasureService;
import nl.app.loopsnelheid.measurement.application.StatisticService;
import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.measurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.measurement.presentation.dto.DefaultMeasureDto;
import nl.app.loopsnelheid.measurement.presentation.dto.MeasureStatisticDto;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class StatisticController
{
    private final StatisticService statisticService;
    private final DefaultMeasureService defaultMeasureService;

    private final UserService userService;

    @GetMapping("/today")
    public MeasureStatisticDto getCurrentDalyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfToday(authenticatedUser);
        DefaultMeasure defaultMeasure = this.defaultMeasureService.getDefaultMeasureBySexAndAge(
                authenticatedUser.getSex(),
                authenticatedUser.getAge()
        );

        return new MeasureStatisticDto(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                new DefaultMeasureDto(defaultMeasure.getAge(), defaultMeasure.getSpeed()),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }

    @GetMapping("/week")
    public MeasureStatisticDto getCurrentWeeklyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentWeek(authenticatedUser);
        DefaultMeasure defaultMeasure = this.defaultMeasureService.getDefaultMeasureBySexAndAge(
                authenticatedUser.getSex(),
                authenticatedUser.getAge()
        );

        return new MeasureStatisticDto(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                new DefaultMeasureDto(defaultMeasure.getAge(), defaultMeasure.getSpeed()),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }
    
    @GetMapping("/month")
    public MeasureStatisticDto getCurrentMonthlyAverage()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        MeasureStatistic measureStatistic = this.statisticService.getAverageMeasuresOfCurrentMonth(authenticatedUser);
        DefaultMeasure defaultMeasure = this.defaultMeasureService.getDefaultMeasureBySexAndAge(
                authenticatedUser.getSex(),
                authenticatedUser.getAge()
        );

        return new MeasureStatisticDto(
                measureStatistic.getStartDate(),
                measureStatistic.getEndDate(),
                measureStatistic.getMeasureStatisticType().toString(),
                measureStatistic.getAverageWalkingSpeed(),
                measureStatistic.getAmountOfMeasures(),
                new DefaultMeasureDto(defaultMeasure.getAge(), defaultMeasure.getSpeed()),
                measureStatistic.getAverageWalkingSpeedEachDay()
        );
    }
}
