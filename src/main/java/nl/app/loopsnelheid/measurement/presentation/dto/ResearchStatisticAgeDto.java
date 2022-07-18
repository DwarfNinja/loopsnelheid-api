package nl.app.loopsnelheid.measurement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResearchStatisticAgeDto
{
    @JsonProperty("average_speed_male")
    public double averageSpeedMale;

    @JsonProperty("average_speed_female")
    public double averageSpeedFemale;
}
