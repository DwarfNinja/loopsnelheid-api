package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDto
{
    public String session;

    @JsonProperty("type")
    public String eDevice;

    public DeviceDto(String session, String eDevice)
    {
        this.session = session;
        this.eDevice = eDevice;
    }
}
