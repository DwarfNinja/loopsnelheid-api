package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDateTime;

public class MeasureStatisticDTO
{
    public final LocalDateTime startDate;
    public final LocalDateTime endDate;

    public final String type;
    public final double averageSpeed;
    public final int amountOfMeasures;

    public MeasureStatisticDTO(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String type,
            double averageSpeed,
            int amountOfMeasures)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.averageSpeed = averageSpeed;
        this.amountOfMeasures = amountOfMeasures;
    }
}
