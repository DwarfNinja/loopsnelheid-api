package nl.app.loopsnelheid.security.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(nullable = false, length = 99999)
    private String deviceInfoJSON;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {}

    public Device(String session, String deviceInfoJSON, EDevice eDevice, EOSDevice eosDevice, User user)
    {
        this.session = session;
        this.deviceInfoJSON = deviceInfoJSON;
        this.eDevice = eDevice;
        this.eosDevice = eosDevice;
        this.user = user;
    }
}