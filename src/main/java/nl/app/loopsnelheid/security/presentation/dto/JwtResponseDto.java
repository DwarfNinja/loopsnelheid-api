package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class JwtResponseDto
{
    @JsonProperty("access_token")
    private final String accessToken;
    private final String email;
    private final Set<String> roles;

    public JwtResponseDto(String accessToken, String email, Set<String> roles)
    {
        this.accessToken = accessToken;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getEmail()
    {
        return email;
    }

    public Set<String> getRoles()
    {
        return roles;
    }
}
