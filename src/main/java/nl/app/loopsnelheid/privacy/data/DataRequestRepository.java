package nl.app.loopsnelheid.privacy.data;

import nl.app.loopsnelheid.privacy.domain.DataRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRequestRepository extends JpaRepository<DataRequest, Long> {
}
