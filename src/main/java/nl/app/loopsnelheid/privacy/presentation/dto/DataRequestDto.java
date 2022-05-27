package nl.app.loopsnelheid.privacy.presentation.dto;

import java.time.LocalDateTime;

public class DataRequestDto
{
    public Long id;
    public String email;

    public String dataRequestStatus;

    public LocalDateTime requestedAt;

    public DataRequestDto(Long id, String email, String dataRequestStatus, LocalDateTime requestedAt)
    {
        this.id = id;
        this.email = email;
        this.dataRequestStatus = dataRequestStatus;
        this.requestedAt = requestedAt;
    }
}
