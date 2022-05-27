package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class JwtResponseDto
{
    @JsonProperty("access_token")
    public final String accessToken;
    public final String email;
    public final Set<String> roles;

    public JwtResponseDto(String accessToken, String email, Set<String> roles)
    {
        this.accessToken = accessToken;
        this.email = email;
        this.roles = roles;
    }
}
