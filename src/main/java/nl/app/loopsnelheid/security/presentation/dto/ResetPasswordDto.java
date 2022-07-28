package nl.app.loopsnelheid.security.presentation.dto;

import javax.validation.constraints.Email;

public class ResetPasswordDto
{
    @Email(message = "Email not valid")
    public String email;
}
