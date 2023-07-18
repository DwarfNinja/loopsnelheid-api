package nl.app.loopsnelheid.security.presentation.dto;

import java.util.UUID;

public class DeleteRequestDto
{
    public UUID jobId;

    public DeleteRequestDto(UUID jobId)
    {
        this.jobId = jobId;
    }
}
