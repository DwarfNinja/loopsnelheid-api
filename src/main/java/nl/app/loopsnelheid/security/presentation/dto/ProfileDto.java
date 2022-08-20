package nl.app.loopsnelheid.security.presentation.dto;

import java.util.Date;
import java.util.Set;

public class ProfileDto
{
    public Long id;
    public String email;

    public String dateOfBirth;

    public String sex;
    public boolean isResearchCandidate;

    public int length;

    public int weight;

    public Set<String> roles;

    public ProfileDto(
            Long id,
            String email,
            String dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            int length,
            int weight,
            Set<String> roles)
    {
        this.id = id;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.isResearchCandidate = isResearchCandidate;
        this.length = length;
        this.weight = weight;
        this.roles = roles;
    }
}
