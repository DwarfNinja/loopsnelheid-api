package nl.app.loopsnelheid.security.presentation.dto;

import java.util.Date;
import java.util.Set;

public class RegisterDto extends UserDto
{
    public String password;

    public RegisterDto(
            Long id,
            String email,
            Date dateOfBirth,
            String sex,
            boolean researchPurposes,
            boolean termsAndConditions,
            boolean privacyStatement,
            int length,
            int weight,
            Set<String> roles)
    {
        super(id, email, dateOfBirth, sex, researchPurposes, termsAndConditions, privacyStatement, length, weight, roles);
    }
}
