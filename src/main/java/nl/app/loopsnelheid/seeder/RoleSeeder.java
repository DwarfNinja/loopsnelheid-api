package nl.app.loopsnelheid.seeder;

import nl.app.loopsnelheid.security.application.RoleService;
import nl.app.loopsnelheid.security.domain.ERole;
import nl.app.loopsnelheid.security.domain.Role;

import java.util.HashSet;
import java.util.Set;

public class RoleSeeder extends ObjectSeeder<Role>
{
    private final RoleService roleService;

    public RoleSeeder(RoleService roleService)
    {
        this.roleService = roleService;
    }

    @Override
    protected void load()
    {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.USER));
        roles.add(new Role(ERole.ADMIN));

        objects.addAll(roles);
    }

    @Override
    public void run()
    {
        load();

        roleService.saveAllRoles(objects);
    }
}
