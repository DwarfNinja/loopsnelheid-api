package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.data.RoleRepository;
import nl.app.loopsnelheid.security.domain.ERole;
import nl.app.loopsnelheid.security.domain.Role;
import nl.app.loopsnelheid.security.domain.exception.RoleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new RoleNotFoundException("Deze rol bestaat niet."));
    }

    public boolean roleExistsByName(String name)
    {
        Optional<Role> role = roleRepository.findByName(ERole.valueOf(name));

        return role.isPresent();
    }

    public Set<Role> provideUserRoles(List<String> roles)
    {
        return roles.stream().map(this::getRoleByName).collect(Collectors.toSet());
    }

    public Role createRole(ERole eRole)
    {
        return new Role(eRole);
    }

    public void saveAllRoles(Set<Role> roles)
    {
        for (Role role : roles)
        {
            if (!roleExistsByName(role.getName().toString()))
            {
                roleRepository.save(role);
            }
        }
    }
}
