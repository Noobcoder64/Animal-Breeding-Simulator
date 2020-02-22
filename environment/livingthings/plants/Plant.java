package environment.livingthings.plants;

import java.util.List;

import environment.livingthings.Food;
import environment.livingthings.LivingThing;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing shared characteristics of animals. Plant extends
 * LivingThing Plants reproduce and get eaten The weather impacts plants and
 * they only reproduce when it is raining
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class Plant extends LivingThing implements Food {

	private static final double BREEDING_PROBABILITY = 0.08; // 0.08

	public static final int FOOD_VALUE = 25;

	/**
	 * Constructor for the plant - creates a plant at a given location on a given
	 * field
	 * 
	 * @param field
	 * @param location
	 */
	public Plant(Field field, Location location) {
		super(field, location);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newPlants) {
		if (isAlive()) {

			if (weather.isRaining()) {
				reproduce(newPlants);
			}
		}
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
	protected boolean canBreed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected LivingThing createOffSpring(Location location) {
		return new Plant(field, location);
	}

	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}

	/**
	 * @return max amount of plants produced when the plant multiplies/breeds
	 */
	@Override
	protected int getMaxLitterSize() {
		return 3;
	}

	@Override
	public void getEaten() {
		setDead();
	}

}
