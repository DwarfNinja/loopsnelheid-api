package nl.app.loopsnelheid.security.presentation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

public class UserDto {
    public Long id;

    @Email(message = "Email not valid")
    @NotBlank(message = "Email can't be empty")
    public String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;

    @NotBlank(message = "Sex can't be empty")
    public String sex;
}
