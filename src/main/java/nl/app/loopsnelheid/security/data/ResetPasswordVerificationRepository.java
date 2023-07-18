package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordVerificationRepository extends JpaRepository<ResetPasswordVerification, Long>
{
    Optional<ResetPasswordVerification> findByTokenAndUserId(String token, Long userId);
}
