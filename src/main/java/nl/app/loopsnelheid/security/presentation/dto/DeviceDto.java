package nl.app.loopsnelheid.security.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDto
{
    public Long id;
    public String session;

    @JsonProperty("device_os")
    public String deviceOs;

    @JsonProperty("device_model")
    public String deviceModel;

    @JsonProperty("type")
    public String eDevice;

    public DeviceDto(Long id, String session, String deviceOs, String deviceModel, String eDevice)
    {
        this.id = id;
        this.session = session;
        this.deviceOs = deviceOs;
        this.deviceModel = deviceModel;
        this.eDevice = eDevice;
    }
}
