package nl.app.loopsnelheid.meassurement.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "measures")
public class Measure
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double walkingSpeed;

    private LocalDateTime registeredAt;

    public Measure() {}

    public Measure(double walkingSpeed, LocalDateTime registeredAt)
    {
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

    public boolean isRegisterDateBetween(LocalDateTime startDate, LocalDateTime endDate)
    {
        return startDate.isBefore(this.registeredAt) && endDate.isAfter(this.registeredAt);
    }
}
