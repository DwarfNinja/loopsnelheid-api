package nl.app.loopsnelheid.security.domain;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role() {}

    public Role(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public Role(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }
}
