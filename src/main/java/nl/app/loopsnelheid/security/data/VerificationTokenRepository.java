package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    Optional<VerificationToken> findByTokenAndUserId(String token, Long userId);

    Optional<VerificationToken> findByDigitalCodeAndUserId(String digitalCode, Long userId);
}
