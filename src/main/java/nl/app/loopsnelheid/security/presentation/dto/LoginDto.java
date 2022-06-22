package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDto
{
    public String email;
    public String password;

    @JsonProperty("device_os")
    public String deviceOs;

    @JsonProperty("device_info")
    public String deviceInfo;
}
