package nl.app.loopsnelheid.security.domain;

import lombok.Builder;
import lombok.Getter;
import nl.app.loopsnelheid.measurement.domain.Measure;
import nl.app.loopsnelheid.privacy.domain.DataRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "unique_email_index", columnList = "email", unique = true),
        })
@Getter
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private boolean isResearchCandidate;

    @OneToOne(mappedBy = "user")
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "user")
    private List<DataRequest> dataRequests;

    @OneToMany(mappedBy = "user")
    private Set<Device> devices;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Measure> measures = new ArrayList<>();

    public User() {}

    public User(
            Long id,
            String email,
            String password,
            Date dateOfBirth,
            Sex sex,
            int length,
            int weight,
            boolean isResearchCandidate,
            VerificationToken verificationToken,
            List<DataRequest> dataRequests,
            Set<Device> devices,
            Set<Role> roles,
            List<Measure> measures) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.length = length;
        this.weight = weight;
        this.isResearchCandidate = isResearchCandidate;
        this.verificationToken = verificationToken;
        this.dataRequests = dataRequests;
        this.devices = devices;
        this.roles = roles;
        this.measures = measures;
    }

    public boolean isVerified()
    {
        return verificationToken.isVerified();
    }

    public String getUsername() {
        return this.email;
    }

    public int getAge()
    {
        LocalDate date = LocalDate.now();
        Period period = Period.between(dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), date);

        return period.getYears();
    }

    public String getBirthYear()
    {
        LocalDate localDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Integer.toString(localDate.getYear());
    }

    public int getAmountOfDevices()
    {
        return devices.size();
    }

    public Device getRandomDeviceExceptGivenSession(String session)
    {
        return devices.stream()
                .filter(device -> !device.getSession().equals(session))
                .findFirst()
                .orElse(null);
    }

    public Device getCurrentMeasureDevice()
    {
        return devices.stream()
                .filter(device -> device.getEDevice().equals(EDevice.MEASURING_DEVICE))
                .findFirst()
                .orElse(null);
    }
}