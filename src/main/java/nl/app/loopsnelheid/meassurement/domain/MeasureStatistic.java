package nl.app.loopsnelheid.meassurement.domain;

import nl.app.loopsnelheid.meassurement.domain.exceptions.NoAvailableMeasuresException;

import java.time.LocalDateTime;
import java.util.List;

public class MeasureStatistic
{
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private final MeasureStatisticType measureStatisticType;

    private final List<Measure> measures;

    public MeasureStatistic(LocalDateTime startDate, LocalDateTime endDate, List<Measure> measures, MeasureStatisticType measureStatisticType)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.measureStatisticType = measureStatisticType;
        this.measures = measures;
    }

    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    public LocalDateTime getEndDate()
    {
        return endDate;
    }

    public MeasureStatisticType getMeasureStatisticType() {
        return measureStatisticType;
    }

    public int getAmountOfMeasures()
    {
        return measures.size();
    }

    public double getAverageWalkingSpeed() throws NoAvailableMeasuresException
    {
        return this.measures.stream()
                .mapToDouble(Measure::getWalkingSpeed)
                .average()
                .orElse(0);
    }
}
