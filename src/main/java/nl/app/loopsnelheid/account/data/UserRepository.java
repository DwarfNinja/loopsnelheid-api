package nl.app.loopsnelheid.account.data;

import nl.app.loopsnelheid.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
