package nl.app.loopsnelheid.measurement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResearchDataRequestDto
{
    @JsonProperty("with_json")
    public final boolean withJson;

    @JsonProperty("with_xml")
    public final boolean withXml;

    @JsonProperty("with_csv")
    public final boolean withCsv;
}
