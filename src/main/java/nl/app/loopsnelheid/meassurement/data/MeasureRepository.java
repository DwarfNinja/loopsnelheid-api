package nl.app.loopsnelheid.meassurement.data;

import nl.app.loopsnelheid.meassurement.domain.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long>
{
    @Query("select m from Measure m where m.registeredAt between ?1 AND ?2 AND user_id = ?3 ORDER BY m.registeredAt")
    List<Measure> findAllBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Long userId);

    @Query("select m from Measure m where m.registeredAt between ?1 AND ?2 ORDER BY m.registeredAt")
    List<Measure> findAllBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
