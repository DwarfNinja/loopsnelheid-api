package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.util.List;

public class DefaultMeasureStatisticDto
{
    public List<DefaultMeasureDto> defaultMaleMeasures;

    public List<DefaultMeasureDto> defaultFemaleMeasures;

    public DefaultMeasureStatisticDto(List<DefaultMeasureDto> defaultMaleMeasures, List<DefaultMeasureDto> defaultFemaleMeasures)
    {
        this.defaultMaleMeasures = defaultMaleMeasures;
        this.defaultFemaleMeasures = defaultFemaleMeasures;
    }
}
