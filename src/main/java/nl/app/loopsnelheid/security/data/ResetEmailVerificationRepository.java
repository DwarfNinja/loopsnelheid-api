package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.ResetEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetEmailVerificationRepository extends JpaRepository<ResetEmailVerification, Long>
{
    Optional<ResetEmailVerification> findByTokenAndUserId(String token, Long userId);
}
