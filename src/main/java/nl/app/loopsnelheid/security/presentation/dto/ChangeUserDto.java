package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class ChangeUserDto
{
    public Long id;

    @Email(message = "Email not valid")
    public String email;
    
    @JsonProperty("date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;

    public String sex;

    @JsonProperty("is_research_candidate")
    public boolean isResearchCandidate;

    public int length;

    public int weight;

    @JsonInclude(NON_NULL)
    @JsonProperty("password")
    public String password;

    public Set<String> roles;

    public ChangeUserDto(
            Long id,
            String email,
            Date dateOfBirth,
            String sex,
            boolean isResearchCandidate,
            int length,
            int weight,
            Set<String> roles
    )
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
