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
	// Characteristics shared by all foxes (class variables).

	// The age at which a fox can start to breed.
	private static final int BREEDING_AGE = 5; // 15
	// The age to which a fox can live.
	private static final int MAX_AGE = 150; // 150
	// The likelihood of a fox breeding.
	private static final double BREEDING_PROBABILITY = 0.08; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 2; // 2
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	// private static final int RABBIT_FOOD_VALUE = 9;
	private static final int FOOD_LEVEL = 20;
	private static final int DAY_STRENGTH = 5;
	private static final int NIGHT_STRENGTH = 15;
	private static int currentStrength;

	private static final Class[] foodSources = { Phoenix.class };

	/**
	 * Create a fox. A fox can be created as a new born (age zero and not hungry) or
	 * with a random age and food level.
	 * 
	 * @param randomAge If true, the fox will have random age and hunger level.
	 * @param field     The field currently occupied.
	 * @param location  The location within the field.
	 */
	public WereWolf(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
		foodLevel = FOOD_LEVEL;
	}

	/**
	 * This is what the fox does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 * 
	 * @param field    The field currently occupied.
	 * @param newFoxes A list to return newly born foxes.
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newAnimals) {
		/*
		 * Increment Age Increment Hunger Reproduce Find Food Eat Food Move
		 */

		this.compete();

		super.act(timeOfDay, weather, newAnimals);
		updateStrength(timeOfDay);
	}

	@Override
	public int getStrength() {
		return currentStrength;
	}

	public void updateStrength(TimeOfDay timeOfDay) {
		if (timeOfDay == TimeOfDay.NIGHT) {
			currentStrength = NIGHT_STRENGTH;

		} else {
			currentStrength = DAY_STRENGTH;
		}
	}

	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}

	@Override
	public int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	@Override
	public WereWolf createAnimal(boolean randomAge, Field field, Location location) {
		return new WereWolf(randomAge, field, location);
	}

	@Override
	protected int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	public Class[] getFoodSources() {
		return foodSources;
	}

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

}
