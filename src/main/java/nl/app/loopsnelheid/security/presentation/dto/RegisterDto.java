package nl.app.loopsnelheid.security.presentation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public class RegisterDto extends UserDto
{
    public String password;

    public RegisterDto(Long id, String email, Date dateOfBirth, String sex, boolean isResearchCandidate, int length, int weight, Set<String> roles)
    {
        super(id, email, dateOfBirth, sex, isResearchCandidate, length, weight, roles);
    }
}
