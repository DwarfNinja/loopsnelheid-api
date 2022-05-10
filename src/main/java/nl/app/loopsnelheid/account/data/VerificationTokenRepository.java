package nl.app.loopsnelheid.account.data;

import nl.app.loopsnelheid.account.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    Optional<VerificationToken> findByTokenAndUserId(String token, Long userId);

    Optional<VerificationToken> findByDigitalCodeAndUserId(String digitalCode, Long userId);
}
