package nl.app.loopsnelheid.measurement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultMeasureStatisticDto
{
    @JsonProperty("default_male_measures")
    public final List<DefaultMeasureDto> defaultMaleMeasures;

    @JsonProperty("default_female_measures")
    public final List<DefaultMeasureDto> defaultFemaleMeasures;
}
