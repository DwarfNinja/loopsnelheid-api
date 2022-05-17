package nl.app.loopsnelheid.meassurement.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeasureStatistic
{
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private final MeasureStatisticType measureStatisticType;

    private final List<Measure> measures;

    public MeasureStatistic(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Measure> measures,
            MeasureStatisticType measureStatisticType)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.measureStatisticType = measureStatisticType;
        this.measures = measures;
    }

    public LocalDate getStartDate()
    {
        return startDate.toLocalDate();
    }

    public LocalDate getEndDate()
    {
        return endDate.toLocalDate();
    }

    public MeasureStatisticType getMeasureStatisticType() {
        return measureStatisticType;
    }

    public int getAmountOfMeasures()
    {
        return measures.size();
    }

    public Map<LocalDate, Double> getAverageWalkingSpeedEachDay()
    {
        Map<LocalDate, Double> averageWalkingSpeeds = new HashMap<>();
        Map<LocalDateTime, List<Measure>> measuresGroupedByDate = measures.stream()
                .collect(Collectors.groupingBy(Measure::getRegisteredAt));

        for (Map.Entry<LocalDateTime, List<Measure>> entry : measuresGroupedByDate.entrySet())
        {
            averageWalkingSpeeds.put(
                    entry.getKey().toLocalDate(),
                    entry.getValue().stream().mapToDouble(Measure::getWalkingSpeed).average().orElse(0)
            );
        }

        return averageWalkingSpeeds;
    }

    public double getAverageWalkingSpeed()
    {
        return measures.stream()
                .mapToDouble(Measure::getWalkingSpeed)
                .average()
                .orElse(0);
    }
}
