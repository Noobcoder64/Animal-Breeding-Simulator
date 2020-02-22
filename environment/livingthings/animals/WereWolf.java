package environment.livingthings.animals;

import java.util.List;

import environment.livingthings.LivingThing;
import environment.livingthings.animals.properties.Predator;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the WereWolf animal in this simulation The WereWolf
 * animal is a Predator
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class WereWolf extends Animal implements Predator {
	// Characteristics shared by all werewolves (class variables).

	// The age at which a werewolf can start to breed.
	private static final int BREEDING_AGE = 5; // 15
	// The age to which a werewolf can live.
	private static final int MAX_AGE = 150; // 150
	// The likelihood of a werewolf breeding.
	private static final double BREEDING_PROBABILITY = 0.08; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 2; // 2

	// The initial food level of a werewolf.
	private static final int INITIAL_FOOD_LEVEL = 20;

	// The strength value of a werewolf at day time (used when competing with other
	// predators).
	private static final int DAY_STRENGTH = 5;

	// The strength value of a werewolf at night time (used when competing with
	// other predators).
	private static final int NIGHT_STRENGTH = 15;

	// The current strenght value of a werewolf (static because all wolves will have
	// the same strength)
	private static int currentStrength;

	// The food sources of a wolf.
	private static final Class[] foodSources = { Phoenix.class };

	/**
	 * {@inheritDoc}
	 */
	public WereWolf(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newAnimals) {
		updateStrength(timeOfDay);
		compete();
		super.act(timeOfDay, weather, newAnimals);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getStrength() {
		return currentStrength;
	}

	/**
	 * Makes a werewolf stronger during the night.
	 * 
	 * @param timeOfDay
	 *            The current time of the day
	 */
	public void updateStrength(TimeOfDay timeOfDay) {
		if (timeOfDay == TimeOfDay.NIGHT) {
			currentStrength = NIGHT_STRENGTH;
		} else {
			currentStrength = DAY_STRENGTH;
		}
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
	public WereWolf createAnimal(boolean randomAge, Field field, Location location) {
		return new WereWolf(randomAge, field, location);
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
	protected int getInitialFoodLevel() {
		return INITIAL_FOOD_LEVEL;
	}

}
