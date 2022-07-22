package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query("select u from User u where u.isResearchCandidate = true ORDER BY u.dateOfBirth")
    List<User> findAllWhereIsResearchCandidate();
}
