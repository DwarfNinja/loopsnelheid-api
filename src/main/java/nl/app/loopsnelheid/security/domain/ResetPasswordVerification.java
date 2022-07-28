package nl.app.loopsnelheid.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password_verifications")
@NoArgsConstructor
public class ResetPasswordVerification
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    private String token;

    private LocalDateTime expiryDate;

    @Setter
    private LocalDateTime verificationDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    @Getter
    private User user;

    public ResetPasswordVerification(String token, LocalDateTime expiryDate, User user)
    {
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    public boolean isExpired()
    {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isVerified()
    {
        return verificationDate != null;
    }

    public static LocalDateTime generateExpiryDate()
    {
        return LocalDateTime.now().plusDays(7);
    }
}
