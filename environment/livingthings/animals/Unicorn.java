package environment.livingthings.animals;

import environment.livingthings.animals.properties.Herbivore;
import environment.livingthings.animals.properties.Prey;
import environment.livingthings.plants.Plant;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the Unicorn animal in this simulation The Unicorn animal
 * is a Prey
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class Unicorn extends Animal implements Prey, Herbivore {
	// Characteristics shared by all unicorns (class variables).

	// The age at which a unicorn can start to breed.
	private static final int BREEDING_AGE = 5;
	// The age to which a unicorn can live.
	private static final int MAX_AGE = 60;
	// The likelihood of a unicorn breeding.
	private static final double BREEDING_PROBABILITY = 0.12; // 0.12
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 8;

	// The initial food level of a unicorn.
	private static final int INITIAL_FOOD_LEVEL = 0;

	// The food value of a unicorn when eaten.
	public static final int FOOD_VALUE = 10;

	// The food sources of a unicorn.
	private static final Class[] foodSources = { Plant.class };

	/**
	 * {@inheritDoc}
	 */
	public Unicorn(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
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
	public int getFoodValue() {
		return FOOD_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Unicorn createAnimal(boolean randomAge, Field field, Location location) {
		return new Unicorn(randomAge, field, location);
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
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getMaxAge() {
		return MAX_AGE;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void getEaten() {
		setDead();
	}

}