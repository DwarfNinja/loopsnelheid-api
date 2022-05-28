package nl.app.loopsnelheid.privacy.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DataRequestDto
{
    public final Long id;
    public final String email;

    @JsonProperty("data_request_status")
    public final String dataRequestStatus;

    @JsonProperty("requested_at")
    public final LocalDateTime requestedAt;
}
