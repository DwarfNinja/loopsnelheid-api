package nl.app.loopsnelheid.security.presentation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

public class UserDto
{
    public Long id;

    public String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;

    public String sex;

    public boolean isResearchCandidate;

    public boolean termsAndConditions;

    public boolean privacyStatement;

    public int length;

    public int weight;

    public Set<String> roles;

    public UserDto(
            Long id,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            boolean termsAndConditions,
            boolean privacyStatement,
            int length,
            int weight,
            Set<String> roles)
    {
        this.id = id;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.isResearchCandidate = isResearchCandidate;
        this.termsAndConditions = termsAndConditions;
        this.privacyStatement = privacyStatement;
        this.length = length;
        this.weight = weight;
        this.roles = roles;
    }
}
