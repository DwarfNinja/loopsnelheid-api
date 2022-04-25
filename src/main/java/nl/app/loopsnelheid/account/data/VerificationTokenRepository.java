package nl.app.loopsnelheid.account.data;

import nl.app.loopsnelheid.account.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
