package nl.app.loopsnelheid.database.seeder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.database.LoadDatabase;
import nl.app.loopsnelheid.meassurement.application.DefaultMeasureService;
import nl.app.loopsnelheid.meassurement.data.DefaultMeasureRepository;
import nl.app.loopsnelheid.security.application.RoleService;
import nl.app.loopsnelheid.security.data.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseSeeder extends Seeder
{
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final List<Seeder> seeders = new ArrayList<>();

    private final RoleRepository roleRepository;
    private final DefaultMeasureRepository defaultMeasureRepository;

    @Override
    protected void load()
    {
        seeders.add(new RoleSeeder(new RoleService(roleRepository)));
        logger.info("All roles has been saved");
        seeders.add(new DefaultMeasureSeeder(new DefaultMeasureService(defaultMeasureRepository)));
        logger.info("All default measures has been saved");
    }

    @Override
    public void run()
    {
        load();
        seeders.forEach(Seeder::run);
        logger.info("Successfully runned all seeders. You ready to go!");
    }
}