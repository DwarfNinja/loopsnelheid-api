package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.RoleRepository;
import nl.app.loopsnelheid.security.domain.ERole;
import nl.app.loopsnelheid.security.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService
{
    private final RoleRepository roleRepository;

    public Role getRoleByName(String name)
    {
        return roleRepository.findByName(ERole.valueOf(name))
                .orElseThrow(() -> new RuntimeException("Error: Deze rol bestaat niet"));
    }

    public Set<Role> provideUserRoles(List<String> roles)
    {
        return roles.stream().map(this::getRoleByName).collect(Collectors.toSet());
    }

    public void saveAllRoles(Set<Role> roles)
    {
        roleRepository.saveAll(roles);
    }
}
