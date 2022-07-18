package nl.app.loopsnelheid.measurement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("aanvrager")
    private final String exportedBy;

    @JsonProperty("aanmaak_datum_bestand")
    private final Date exportedOn;

    @JsonProperty("gebruiker")
    private List<ResearchDataCandidate> candidates;
}
