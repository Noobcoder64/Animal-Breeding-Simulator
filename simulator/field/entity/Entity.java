package simulator.field.entity;

import java.util.List;

import environment.livingthings.LivingThing;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;

/**
 * A class representing shared characteristics of all Living Things in this
 * simulation
 * 
 * Living things must be alive, have a location, and be on a field.
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public abstract class Entity {
    // The animal's field.
    protected Field field;
    // The animal's position in the field.
    protected Location location;

    /**
     * Create a new animal at location in field.
     * 
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Entity(Field field, Location location) {
        this.field = field;
        setLocation(location);
    }

    /**
     * Place the animal at the new location in the given field.
     * 
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's location.
     * 
     * @return The animal's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Return the animal's field.
     * 
     * @return The animal's field.
     */
    public Field getField() {
        return field;
    }

    /**
     * Make this animal act - that is: make it do whatever it wants/needs to do.
     * 
     * @param timeOfDay
     * @param weather
     * 
     * @param newLivingThings A list to receive newly born animals.
     */
    public abstract void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newLivingThings);

    protected abstract boolean canBeRemoved();
}
