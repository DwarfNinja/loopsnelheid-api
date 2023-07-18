package nl.app.loopsnelheid.measurement.domain;

import nl.app.loopsnelheid.security.domain.Sex;

import javax.persistence.*;

@Entity
@Table(name = "default_measures")
public class DefaultMeasure
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double speed;

    public DefaultMeasure() {}

    public DefaultMeasure(Long id, Sex sex, int age, double speed)
    {
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.speed = speed;
    }

    public DefaultMeasure(Sex sex, int age, double speed)
    {
        this.sex = sex;
        this.age = age;
        this.speed = speed;
    }

    public int getAge()
    {
        return age;
    }

    public double getSpeed()
    {
        return speed;
    }
}
