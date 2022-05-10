package nl.app.loopsnelheid.account.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String digitalCode;
    @Getter
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    @Setter
    private LocalDateTime verificationDate;

    public VerificationToken(Long id, String digitalCode, String token, User user, LocalDateTime expiryDate, LocalDateTime verificationDate) {
        this.id = id;
        this.digitalCode = digitalCode;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.verificationDate = verificationDate;
    }

    public User getUser() {
        return user;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isVerified() {
        return verificationDate != null;
    }

    public static LocalDateTime generateExpiryDate() {
        return LocalDateTime.now().plusDays(7);
    }
}
