package nl.app.loopsnelheid.meassurement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ResearchData
{
    private final String exportedBy;
    private final LocalDate exportedOn;
    private List<ResearchDataCandidate> candidates;
}
