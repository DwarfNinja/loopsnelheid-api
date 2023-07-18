package nl.app.loopsnelheid.security.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Getter
@Setter
public class Device
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String session;

    @Enumerated(EnumType.STRING)
    private EDevice eDevice;

    @Enumerated(EnumType.STRING)
    private EOSDevice eosDevice;

    @Column(nullable = false)
    private String deviceModel;

    private LocalDateTime signedInAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {}

    public Device(String session, String deviceModel, EDevice eDevice, EOSDevice eosDevice, User user)
    {
        this.session = session;
        this.deviceModel = deviceModel;
        this.eDevice = eDevice;
        this.eosDevice = eosDevice;
        this.user = user;
        this.signedInAt = LocalDateTime.now();
    }
}