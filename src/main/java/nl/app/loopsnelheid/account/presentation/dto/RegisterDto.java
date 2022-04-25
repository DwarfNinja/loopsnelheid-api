package nl.app.loopsnelheid.account.presentation.dto;

import javax.validation.constraints.Size;

public class RegisterDto extends UserDto {
    @Size(min = 5, message = "Password must be longer than 5 characters")
    public String password;
}
