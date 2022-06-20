package nl.app.loopsnelheid.database;

import nl.app.loopsnelheid.database.seeder.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
    @Value("${database.seed.state}")
    private boolean finished;
    @Bean
    CommandLineRunner initDatabase(DatabaseSeeder databaseSeeder)
    {
        return args -> {
            if (!finished) {
                databaseSeeder.run();
            }
        };
    }
}
