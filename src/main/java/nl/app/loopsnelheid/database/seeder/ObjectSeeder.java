package nl.app.loopsnelheid.database.seeder;

import java.util.HashSet;
import java.util.Set;

public abstract class ObjectSeeder<T> extends Seeder
{
    protected final Set<T> objects = new HashSet<>();
}
