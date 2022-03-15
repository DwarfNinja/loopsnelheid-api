package nl.app.loopsnelheid.meassurement.data;

import nl.app.loopsnelheid.meassurement.domain.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long>
{
    List<Measure> findAllByRegisteredAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
