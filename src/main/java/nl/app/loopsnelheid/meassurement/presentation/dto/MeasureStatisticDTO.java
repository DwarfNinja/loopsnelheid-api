package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDateTime;

public class MeasureStatisticDTO
{
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private final String type;
    private final double averageSpeed;
    private final int amountOfMeasures;

    public MeasureStatisticDTO(LocalDateTime startDate, LocalDateTime endDate, String type, double averageSpeed, int amountOfMeasures)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.averageSpeed = averageSpeed;
        this.amountOfMeasures = amountOfMeasures;
    }

    public String getType()
    {
        return type;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public int getAmountOfMeasures() {
        return amountOfMeasures;
    }
}
