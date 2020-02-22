package environment.livingthings.animals;

import java.util.List;


import environment.livingthings.Food;
import environment.livingthings.LivingThing;
import environment.livingthings.animals.properties.Predator;
import environment.time.TimeOfDay;
import environment.weather.Weather;

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
	// Characteristics shared by all cerberuses (class variables).

	// The age at which a cerberus can start to breed.
	private static final int BREEDING_AGE = 5; // 15
	// The age to which a cerberus can live.
	private static final int MAX_AGE = 120; // 80
	// The likelihood of a cerberus breeding.
	private static final double BREEDING_PROBABILITY = 0.2; // 0.08
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 4; // 2

	// The initial food level of a cerberus.
	private static final int INITIAL_FOOD_LEVEL = 30;

	// The strength value of a cerberus (used when competing with other predators).
	private static final int STRENGTH = 10;


	// The food sources of a cerberus.
	private static final Class[] foodSources = { Phoenix.class };

	/**
	 * {@inheritDoc}
	 */
	public Cerberus(boolean randomAge, Field field, Location location) {
		super(randomAge, field, location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cerberus createAnimal(boolean randomAge, Field field, Location location) {
		return new Cerberus(randomAge, field, location);
	}

	/**
	 * {@inheritDoc} Since a cerberus has 3 heads, it will eat 3 times at once.
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newAnimals) {
		/*
		 * Increment Age Increment Hunger Reproduce Find Food Eat Food Move
		 */
		compete();
		super.act(timeOfDay, weather, newAnimals);

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
	protected int getInitialFoodLevel() {
		return INITIAL_FOOD_LEVEL;
	}

}
