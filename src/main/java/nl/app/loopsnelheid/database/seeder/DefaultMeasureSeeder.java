package nl.app.loopsnelheid.database.seeder;

import nl.app.loopsnelheid.measurement.application.DefaultMeasureService;
import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.security.domain.Sex;

import java.util.HashSet;
import java.util.Set;

public class DefaultMeasureSeeder extends ObjectSeeder<DefaultMeasure>
{
    private final DefaultMeasureService defaultMeasureService;

    public DefaultMeasureSeeder(DefaultMeasureService defaultMeasureService)
    {
        this.defaultMeasureService = defaultMeasureService;
    }

    @Override
    protected void load()
    {
        Set<DefaultMeasure> defaultMeasures = new HashSet<>();

        int age = 20;

        while (age <= 29)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 4.9));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 4.8));
            age++;
        }

        while (age <= 39)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 5.2));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 4.8));
            age++;
        }

        while (age <= 49)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 5.2));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 5));
            age++;
        }

        while (age <= 59)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 5.2));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 4.7));
            age++;
        }

        while (age <= 69)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 4.8));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 4.5));
            age++;
        }

        while (age <= 79)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 4.5));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 4.1));
            age++;
        }

        while (age <= 99)
        {
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.MALE, age, 3.5));
            defaultMeasures.add(defaultMeasureService.createDefaultMeasure(Sex.FEMALE, age, 3.4));
            age++;
        }

        objects.addAll(defaultMeasures);
    }

    @Override
    public void run()
    {
        load();

        defaultMeasureService.saveAllDefaultMeasures(objects);
    }
}
