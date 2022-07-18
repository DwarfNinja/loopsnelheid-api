package nl.app.loopsnelheid.measurement.domain;

import nl.app.loopsnelheid.security.domain.User;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Measure() {}

    public Measure(double walkingSpeed, LocalDateTime registeredAt, User user)
    {
        this.walkingSpeed = walkingSpeed;
        this.registeredAt = registeredAt;
        this.user = user;
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
        return startDate.isBefore(registeredAt) && endDate.isAfter(registeredAt);
    }
}
