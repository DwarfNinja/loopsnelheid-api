package nl.app.loopsnelheid.measurement.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MeasureDto
{
    public final Long id;

    @JsonProperty("walking_speed")
    public final double walkingSpeed;

    @JsonProperty("registered_at")
    public final LocalDateTime registeredAt;
}
