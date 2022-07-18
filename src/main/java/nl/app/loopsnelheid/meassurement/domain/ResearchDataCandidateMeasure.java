package nl.app.loopsnelheid.meassurement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ResearchDataCandidateMeasure
{
    private final Long id;
    private final LocalDateTime registeredAt;
    private final double walkingSpeed;
}
