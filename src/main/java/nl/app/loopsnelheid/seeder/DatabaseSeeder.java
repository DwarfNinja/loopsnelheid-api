package nl.app.loopsnelheid.seeder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.RoleService;
import nl.app.loopsnelheid.security.data.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseSeeder extends Seeder
{
    private final List<Seeder> seeders = new ArrayList<>();

    private final RoleRepository roleRepository;

    @Override
    protected void load()
    {
        seeders.add(new RoleSeeder(new RoleService(roleRepository)));
    }

    @Override
    public void run()
    {
        load();
        seeders.forEach(Seeder::run);
    }
}
