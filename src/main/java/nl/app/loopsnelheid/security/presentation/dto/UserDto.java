package nl.app.loopsnelheid.security.presentation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

public class UserDto
{
    public Long id;

    @Email(message = "Email not valid")
    @NotBlank(message = "Email can't be empty")
    public String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;

    @NotBlank(message = "Sex can't be empty")
    public String sex;

    public boolean isResearchCandidate;

    public int length;

    public int weight;

    public Set<RoleDto> roles;

    public UserDto(Long id, String email, Date dateOfBirth, String sex, boolean isResearchCandidate, int length, int weight, Set<RoleDto> roles)
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
