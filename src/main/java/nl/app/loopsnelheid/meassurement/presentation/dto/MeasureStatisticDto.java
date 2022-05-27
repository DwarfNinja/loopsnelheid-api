package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDate;
import java.util.Map;

public class MeasureStatisticDto
{
    public final LocalDate startDate;
    public final LocalDate endDate;
    public final String type;
    public final double averageSpeed;
    public final int amountOfMeasures;

    public final DefaultMeasureDto defaultMeasureBasedOnProfile;

    public final Map<LocalDate, Double> averageMeasure;

    public MeasureStatisticDto(
            LocalDate startDate,
            LocalDate endDate,
            String type,
            double averageSpeed,
            int amountOfMeasures,
            Map<LocalDate, Double> averageMeasures,
            DefaultMeasureDto defaultMeasureBasedOnProfile)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.averageSpeed = averageSpeed;
        this.amountOfMeasures = amountOfMeasures;
        this.averageMeasure = averageMeasures;
        this.defaultMeasureBasedOnProfile = defaultMeasureBasedOnProfile;
    }
}
