package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class MeasureStatisticDTO
{
    public final LocalDateTime startDate;
    public final LocalDateTime endDate;

    public final String type;
    public final double averageSpeed;
    public final int amountOfMeasures;
    public final Map<LocalDate, Double> averageMeasure;

    public MeasureStatisticDTO(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String type,
            double averageSpeed,
            int amountOfMeasures,
            Map<LocalDate, Double> averageMeasures)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.averageSpeed = averageSpeed;
        this.amountOfMeasures = amountOfMeasures;
        this.averageMeasure = averageMeasures;
    }
}
