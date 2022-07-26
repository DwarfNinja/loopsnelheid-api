package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ChangePersonalInformationDto
{
    @JsonProperty("date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;

    public String sex;

    @JsonProperty("is_research_candidate")
    public boolean isResearchCandidate;

    public int length;

    public int weight;

    public ChangePersonalInformationDto(Date dateOfBirth, String sex, boolean isResearchCandidate, int length, int weight)
    {
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.isResearchCandidate = isResearchCandidate;
        this.length = length;
        this.weight = weight;
    }
}
