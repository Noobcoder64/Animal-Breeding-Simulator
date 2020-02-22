package environment.livingthings;

import java.util.List;
import java.util.Random;

import simulator.Randomizer;
import simulator.field.Field;
import simulator.field.entity.Entity;
import simulator.field.entity.Location;

/**
 * A class representing shared characteristics of all Living Things in this
 * simulation A Living Thing is a Entity Living things must be alive, have a
 * location, and be on a field.
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public abstract class LivingThing extends Entity {

	protected static final Random rand = Randomizer.getRandom();

	// Whether the animal is alive or not.
	private boolean alive;

	/**
	 * Create a new Living Thing at location in field.
	 * 
	 * @param field    The field currently occupied.
	 * @param location The location within the field.
	 */
	public LivingThing(Field field, Location location) {
		super(field, location);
		alive = true;
	}

	/**
	 * Check whether the living thing is alive or not
	 * 
	 * @return true if the living thing is still alive.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Indicate that the living thing is no longer alive. It is removed from the
	 * field.
	 */
	public void setDead() {
		alive = false;
		if (location != null) {
			field.clear(location);
			location = null;
			field = null;
		}
	}

	@Override
	protected boolean canBeRemoved() {
		return !isAlive();
	}

	/**
	 * Check whether or not this living thing is reproducing at this step.
	 * Reproduced living things will be added to free adjacent locations
	 * 
	 * @param newAnimals A list to return newly added living things
	 */
	protected void reproduce(List<LivingThing> newLivingThings) {
		// New foxes are born into adjacent locations.
		// Get a list of adjacent free locations.
		Field field = getField();
		List<Location> free = field.getFreeAdjacentLocations(getLocation());
		int offSprings = breed();
		for (int b = 0; b < offSprings && free.size() > 0; b++) {
			Location location = free.remove(0);
			LivingThing offSpring = createOffSpring(location);
			newLivingThings.add(offSpring);
		}
	}

	/**
	 * Generate a number representing the number of births, if it can breed.
	 * 
	 * @return The number of births (may be zero).
	 */
	private int breed() {
		int births = 0;
		if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
			births = rand.nextInt(getMaxLitterSize()) + 1;
		}
		return births;
	}

	/**
	 * @return whether or not a Living Thing can breed (at this time)
	 */
	protected abstract boolean canBreed();

	/**
	 * @return a newly created LivingThing
	 */
	protected abstract LivingThing createOffSpring(Location location);

	/**
	 * @return the probability a living thing can breed
	 */
	protected abstract double getBreedingProbability();

	/**
	 * @return the maximum amount of living things which can be bred
	 */
	protected abstract int getMaxLitterSize();

}
