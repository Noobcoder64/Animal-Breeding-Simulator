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
	// Characteristics shared by all foxes (class variables).

	// The age at which a fox can start to breed.
	private static final int BREEDING_AGE = 15; // 15
	// The age to which a fox can live.
	private static final int MAX_AGE = 150; // 150
	// The likelihood of a fox breeding.
	private static final double BREEDING_PROBABILITY = 0.08; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 2; // 2
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	// private static final int RABBIT_FOOD_VALUE = 9;
	private static final int FOOD_LEVEL = 12;
	private static final int STRENGTH = 20;

	private static final Class[] foodSources = { Unicorn.class };

	public Serpent(boolean randomAge, Field field, Location location) {
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
		if (weather.isRaining()) {
			super.act(timeOfDay, weather, newAnimals);
		} else {
			applyDiseases();
			incrementAge();
			incrementHunger();

			if (!isAlive())
				return;
			reproduce(newAnimals);
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

	/**
	 * A fox can breed if it has reached the breeding age.
	 */
	@Override
	protected boolean canBreed() {
		if (age < BREEDING_AGE)
			return false;
		return true;
	}

	@Override
	public Serpent createAnimal(boolean randomAge, Field field, Location location) {
		return new Serpent(randomAge, field, location);
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

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

}
