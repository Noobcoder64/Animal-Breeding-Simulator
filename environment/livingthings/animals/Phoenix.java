package environment.livingthings.animals;


import environment.livingthings.animals.properties.Prey;
import environment.livingthings.plants.Plant;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the Phoenix animal in this simulation The Phoenix animal
 * is a Prey
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class Phoenix extends Animal implements Prey {
	// Characteristics shared by all phoenixes (class variables).

	// The age at which a phoenix can start to breed.
	private static final int BREEDING_AGE = 5;
	// The age to which a phoenix can live.
	private static final int MAX_AGE = 70;
	// The likelihood of a phoenix breeding.
	private static final double BREEDING_PROBABILITY = 0.14;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 4;

	// The initial food level of a phoenix.
	private static final int INITIAL_FOOD_LEVEL = 30;

	// The food value of a phoenix when eaten.
	public static final int FOOD_VALUE = 9;


	// The food sources of a phoenix.
	private static final Class[] foodSources = { Plant.class };

	/**
	 * {@inheritDoc}
	 */
	public Phoenix(boolean randomAge, Field field, Location location) {
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
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

	@Override
	public int getFoodValue() {
		return FOOD_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Phoenix createAnimal(boolean randomAge, Field field, Location location) {
		return new Phoenix(randomAge, field, location);
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
	protected int getInitialFoodLevel() {
		return INITIAL_FOOD_LEVEL;
	}

	@Override
	public boolean canMove(TimeOfDay timeOfDay, Weather weather) {
		return timeOfDay!=TimeOfDay.NIGHT;
	}
}
