package environment.livingthings.animals;

import java.util.List;

import environment.livingthings.LivingThing;
import environment.livingthings.animals.properties.Predator;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the Serpent animal in this simulation The Serpent animal
 * is a Predator
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */

public class Serpent extends Animal implements Predator {
	// Characteristics shared by all serpents (class variables).

	// The age at which a serpent can start to breed.
	private static final int BREEDING_AGE = 15; // 15
	// The age to which a serpent can live.
	private static final int MAX_AGE = 150; // 150
	// The likelihood of a serpent breeding.
	private static final double BREEDING_PROBABILITY = 0.08; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 2; // 2

	// The initial food level of a serpent.
	private static final int INITIAL_FOOD_LEVEL = 12;

	// The strength value of a serpent (used when competing with other predators).
	private static final int STRENGTH = 20;

	// The food sources of a serpent.
	private static final Class[] foodSources = { Unicorn.class };

	/**
	 * {@inheritDoc}
	 */
	public Serpent(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newAnimals) {
		compete();
		super.act(timeOfDay, weather, newAnimals);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStrength() {
		return STRENGTH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getMaxAge() {
		return MAX_AGE;
	}

	/**
	 * {@inheritDoc} No mate required.
	 */
	@Override
	protected boolean canBreed() {
		if (age < BREEDING_AGE)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Serpent createAnimal(boolean randomAge, Field field, Location location) {
		return new Serpent(randomAge, field, location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class[] getFoodSources() {
		return foodSources;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canMove(TimeOfDay timeOfDay, Weather weather) {
		return !weather.isRaining();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getInitialFoodLevel() {
		return INITIAL_FOOD_LEVEL;
	}
}
