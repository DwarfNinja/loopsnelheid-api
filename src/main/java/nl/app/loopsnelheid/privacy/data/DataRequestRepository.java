package nl.app.loopsnelheid.privacy.data;

import nl.app.loopsnelheid.privacy.domain.DataRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataRequestRepository extends JpaRepository<DataRequest, Long> {
    @Query("select r from DataRequest r where user_id = ?1")
    List<DataRequest> findAllByUserId(Long userId);
}
