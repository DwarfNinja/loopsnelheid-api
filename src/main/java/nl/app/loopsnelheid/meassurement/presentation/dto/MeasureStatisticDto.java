package nl.app.loopsnelheid.meassurement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
public class MeasureStatisticDto
{
    @JsonProperty("start_date")
    public final LocalDate startDate;

    @JsonProperty("end_date")
    public final LocalDate endDate;

    public final String type;

    @JsonProperty("average_speed")
    public final double averageSpeed;

    @JsonProperty("amount_of_measures")
    public final int amountOfMeasures;

    @JsonProperty("default_measure_based_on_profile")
    public final DefaultMeasureDto defaultMeasureBasedOnProfile;

    @JsonProperty("average_measure")
    public final Map<LocalDate, Double> averageMeasure;
}
