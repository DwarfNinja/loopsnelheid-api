package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.util.List;

public class DefaultMeasureStatisticDto
{
    public final List<DefaultMeasureDto> defaultMaleMeasures;

    public final List<DefaultMeasureDto> defaultFemaleMeasures;

    public DefaultMeasureStatisticDto(List<DefaultMeasureDto> defaultMaleMeasures, List<DefaultMeasureDto> defaultFemaleMeasures)
    {
        this.defaultMaleMeasures = defaultMaleMeasures;
        this.defaultFemaleMeasures = defaultFemaleMeasures;
    }
}
