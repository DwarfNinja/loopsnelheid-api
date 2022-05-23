package nl.app.loopsnelheid.account.data;

import nl.app.loopsnelheid.account.domain.DataRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRequestRepository extends JpaRepository<DataRequest, Long> {
}
