package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordDto
{
    @JsonProperty("old_password")
    public String oldPassword;

    @JsonProperty("new_password")
    public String newPassword;

    public ChangePasswordDto(String oldPassword, String newPassword)
    {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
