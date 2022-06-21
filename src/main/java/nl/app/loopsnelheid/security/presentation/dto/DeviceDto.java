package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDto
{
    public Long id;
    public String session;

    @JsonProperty("type")
    public String eDevice;

    public DeviceDto(Long id, String session, String eDevice)
    {
        this.id = id;
        this.session = session;
        this.eDevice = eDevice;
    }
}
