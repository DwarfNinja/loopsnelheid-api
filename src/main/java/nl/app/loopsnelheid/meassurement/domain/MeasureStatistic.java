package nl.app.loopsnelheid.meassurement.domain;

import java.util.List;
import java.util.NoSuchElementException;

public class MeasureStatistic
{
    private final MeasureStatisticType measureStatisticType;

    private final List<Measure> measures;

    public MeasureStatistic(List<Measure> measures, MeasureStatisticType measureStatisticType)
    {
        this.measureStatisticType = measureStatisticType;
        this.measures = measures;
    }

    public MeasureStatisticType getMeasureStatisticType() {
        return measureStatisticType;
    }

    public int getAmountOfMeasures()
    {
        return measures.size();
    }

    public double getAverageWalkingSpeed()
    {
        return this.measures.stream()
                .mapToDouble(Measure::getWalkingSpeed)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }
}
