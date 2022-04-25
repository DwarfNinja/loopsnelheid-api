package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDateTime;

public class MeasureDTO
{
    public final Long id;

    public final double walkingSpeed;

    public final LocalDateTime registeredAt;

    public MeasureDTO(Long id, double walkingSpeed, LocalDateTime registeredAt)
    {
        this.id = id;
        this.walkingSpeed = walkingSpeed;
        this.registeredAt = registeredAt;
    }
}
