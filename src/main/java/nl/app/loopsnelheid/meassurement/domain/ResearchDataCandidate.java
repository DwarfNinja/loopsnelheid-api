package nl.app.loopsnelheid.meassurement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nl.app.loopsnelheid.security.domain.Sex;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ResearchDataCandidate
{
    private final Long id;
    private final Sex sex;
    private final String birthYear;
    private final int length;
    private final int weight;
    private List<ResearchDataCandidateMeasure> measures;
}
