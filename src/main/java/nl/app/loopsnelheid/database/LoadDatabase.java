package nl.app.loopsnelheid.database;

import nl.app.loopsnelheid.database.seeder.DatabaseSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
    @Bean
    CommandLineRunner initDatabase(DatabaseSeeder databaseSeeder)
    {
        return args -> {
            databaseSeeder.run();
        };
    }
}
