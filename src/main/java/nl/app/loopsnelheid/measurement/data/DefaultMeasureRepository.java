package nl.app.loopsnelheid.measurement.data;

import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.security.domain.Sex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DefaultMeasureRepository extends JpaRepository<DefaultMeasure, Long>
{
    List<DefaultMeasure> findAllBySex(Sex sex);
    Optional<DefaultMeasure> findBySexAndAge(Sex sex, int age);
}
