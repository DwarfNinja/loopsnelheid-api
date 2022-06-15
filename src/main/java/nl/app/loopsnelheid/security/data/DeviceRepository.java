package nl.app.loopsnelheid.security.data;

import nl.app.loopsnelheid.security.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long>
{
    Optional<Device> findBySession(String session);
}
