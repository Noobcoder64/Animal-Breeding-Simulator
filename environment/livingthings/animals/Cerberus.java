package environment.livingthings.animals;

import java.util.List;
import java.util.Random;

import environment.livingthings.Food;
import environment.livingthings.LivingThing;
import environment.livingthings.animals.properties.Predator;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.Randomizer;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the Cerberus animal in this simulation The Cerebrus
 * animal is a Predator
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class Cerberus extends Animal implements Predator {
	// Characteristics shared by all foxes (class variables).

	// The age at which a fox can start to breed.
	private static final int BREEDING_AGE = 5; // 15
	// The age to which a fox can live.
	private static final int MAX_AGE = 120; // 80
	// The likelihood of a fox breeding.
	private static final double BREEDING_PROBABILITY = 0.2; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 4; // 2
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	// private static final int RABBIT_FOOD_VALUE = 9;

	private static final int STRENGTH = 10;


	private static final Class[] foodSources = { Phoenix.class };

	public Cerberus(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
	}

	@Override
	public Cerberus createAnimal(boolean randomAge, Field field, Location location) {
		return new Cerberus(randomAge, field, location);
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

		super.act(timeOfDay, weather, newAnimals);
		compete();

		// this allows the Cerberus to try to eat two more times - this is a unique part
		// of the Cerberus act procedure.
		Location newLocation = null;
		for (int i = 0; i < 2; i++) {
			if (!isAlive())
				return;
			Food food = findFood();

			if (!(food == null))
				newLocation = food.getLocation();
			// Move towards a source of food if found.

			move(newLocation);
		}
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	protected int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
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
	public Class[] getFoodSources() {
		return foodSources;
	}

}
