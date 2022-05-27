package nl.app.loopsnelheid.privacy.presentation.dto;

import java.time.LocalDateTime;

public class DataRequestDto
{
    public final Long id;
    public final String email;
    public final String dataRequestStatus;

    public final LocalDateTime requestedAt;

    public DataRequestDto(Long id, String email, String dataRequestStatus, LocalDateTime requestedAt)
    {
        this.id = id;
        this.email = email;
        this.dataRequestStatus = dataRequestStatus;
        this.requestedAt = requestedAt;
    }
}
