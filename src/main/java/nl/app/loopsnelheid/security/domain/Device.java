package nl.app.loopsnelheid.security.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "devices")
@Getter
public class Device
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String session;

    @Enumerated(EnumType.STRING)
    private EDevice eDevice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {}

    public Device(String session, EDevice eDevice, User user)
    {
        this.session = session;
        this.eDevice = eDevice;
        this.user = user;
    }
}