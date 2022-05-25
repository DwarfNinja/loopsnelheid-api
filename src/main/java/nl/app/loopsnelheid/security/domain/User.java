package nl.app.loopsnelheid.security.domain;

import lombok.Builder;
import lombok.Getter;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.privacy.domain.DataRequest;

import javax.persistence.*;
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

    @OneToOne(mappedBy = "user")
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "user")
    private List<DataRequest> dataRequests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Measure> measures = new HashSet<>();

    public User() {}

    public User(
            Long id,
            String email,
            String password,
            Date dateOfBirth,
            Sex sex,
            VerificationToken verificationToken,
            List<DataRequest> dataRequests,
            Set<Role> roles,
            Set<Measure> measures) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.verificationToken = verificationToken;
        this.dataRequests = dataRequests;
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
}