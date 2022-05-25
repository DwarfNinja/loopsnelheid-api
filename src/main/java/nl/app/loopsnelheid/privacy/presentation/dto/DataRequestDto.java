package nl.app.loopsnelheid.privacy.presentation.dto;

import java.time.LocalDateTime;

public class DataRequestDto {
    public String email;

    public String dataRequestStatus;

    public LocalDateTime requestedAt;

    public DataRequestDto(String email, String dataRequestStatus, LocalDateTime requestedAt) {
        this.email = email;
        this.dataRequestStatus = dataRequestStatus;
        this.requestedAt = requestedAt;
    }
}
