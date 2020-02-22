package environment.livingthings.animals;

import java.util.Random;

import environment.livingthings.animals.components.Gender;
import environment.livingthings.animals.properties.Prey;
import environment.livingthings.plants.Plant;
import simulator.Randomizer;
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
	// Characteristics shared by all rabbits (class variables).

	// The age at which a rabbit can start to breed.
	private static final int BREEDING_AGE = 5;
	// The age to which a rabbit can live.
	private static final int MAX_AGE = 70;
	// The likelihood of a rabbit breeding.
	private static final double BREEDING_PROBABILITY = 0.14;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 4;
	// A shared random number generator to control breeding.
	private static final int FOOD_LEVEL = 30;
	public static final int FOOD_VALUE = 9;

	private static final Random rand = Randomizer.getRandom();

	private static final Class[] foodSources = { Plant.class };

	// Individual characteristics (instance fields).

	/**
	 * Create a new rabbit. A rabbit may be created with age zero (a new born) or
	 * with a random age.
	 * 
	 * @param randomAge If true, the rabbit will have a random age.
	 * @param field     The field currently occupied.
	 * @param location  The location within the field.
	 */
	public Phoenix(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
		age = 0;
		if (randomAge) {
			age = rand.nextInt(MAX_AGE);
		}
		foodLevel = FOOD_LEVEL;
	}

	@Override
	public Class[] getFoodSources() {
		return foodSources;
	}

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

	@Override
	public int getFoodValue() {
		return FOOD_VALUE;
	}

	@Override
	public Phoenix createAnimal(boolean randomAge, Field field, Location location) {
		return new Phoenix(randomAge, field, location);
	}

	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}

	@Override
	protected int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	@Override
	protected int getMaxAge() {
		return MAX_AGE;
	}

}
