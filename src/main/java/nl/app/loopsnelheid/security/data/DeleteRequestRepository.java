package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeleteRequestRepository extends JpaRepository<DeleteRequest, Long> {
    Optional<DeleteRequest> findByUser(User user);

    void deleteByJobId(UUID jobId);
}
