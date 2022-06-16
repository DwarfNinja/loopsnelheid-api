package nl.app.loopsnelheid.meassurement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResearchStatisticAge
{
    private final double averageSpeedMale;
    private final double averageSpeedFemale;
}
