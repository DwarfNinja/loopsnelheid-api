package nl.app.loopsnelheid.meassurement.presentation.dto;

import java.time.LocalDateTime;

public class MeasureDTO
{
    private final Long id;

    private final double walkingSpeed;

    private final LocalDateTime registeredAt;

    public MeasureDTO(Long id, double walkingSpeed, LocalDateTime registeredAt)
    {
        this.id = id;
        this.walkingSpeed = walkingSpeed;
        this.registeredAt = registeredAt;
    }

    public Long getId()
    {
        return id;
    }

    public double getWalkingSpeed()
    {
        return walkingSpeed;
    }

    public LocalDateTime getRegisteredAt()
    {
        return registeredAt;
    }
}
