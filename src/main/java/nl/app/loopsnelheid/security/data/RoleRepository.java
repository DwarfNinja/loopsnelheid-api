package nl.app.loopsnelheid.security.data;


import nl.app.loopsnelheid.security.domain.ERole;
import nl.app.loopsnelheid.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}