package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class JwtResponseDto
{
    @JsonProperty("access_token")
    public final String accessToken;
    public final String email;
    public final Set<String> roles;

    @JsonProperty("device")
    public final DeviceDto deviceDto;

}