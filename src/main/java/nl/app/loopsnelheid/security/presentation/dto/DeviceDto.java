package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDto
{
    public Long id;
    public String session;

    @JsonProperty("device_info")
    public String deviceInfo;

    @JsonProperty("type")
    public String eDevice;

    public DeviceDto(Long id, String session, String deviceInfo, String eDevice)
    {
        this.id = id;
        this.session = session;
        this.deviceInfo = deviceInfo;
        this.eDevice = eDevice;
    }
}
