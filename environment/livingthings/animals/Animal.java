package environment.livingthings.animals;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import environment.livingthings.Food;
import environment.livingthings.LivingThing;
import environment.livingthings.animals.components.Gender;
import environment.livingthings.animals.components.diseases.Disease;
import environment.time.TimeOfDay;
import environment.weather.Weather;
import simulator.field.Field;
import simulator.field.entity.Entity;
import simulator.field.entity.Location;

/**
 * A class representing shared characteristics of animals.
 * Animal extends LivingThing 
 * Animals breed, age, move, eat, act, get diseases, and die. 
 * Some animals are impacted by the weather when they act
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and Michael Kölling
 * @version 2019.02.20
 */
public abstract class Animal extends LivingThing {
	// Individual characteristics (instance fields).
	// The fox's age.
	protected int age;
	// The fox's food level, which is increased by eating rabbits.
	protected int foodLevel;

	protected Gender gender;

	protected Set<Disease> diseases;

	/**
	 * Create a new animal at location in field.
	 * 
	 * @param field    The field currently occupied.
	 * @param location The location within the field.
	 */
	public Animal(boolean randomAge, Field field, Location location) {
		super(field, location);
		if (randomAge) {
			age = rand.nextInt(getMaxAge());
			foodLevel = rand.nextInt(Unicorn.FOOD_VALUE);
		} else {
			age = 0;
			foodLevel = 10;
		}
		gender = rand.nextBoolean() ? Gender.MALE : Gender.FEMALE;
		diseases = new HashSet<>();
	}

	public Gender getGender() {
		return gender;
	}

	abstract Animal createAnimal(boolean randomAge, Field field, Location location);

	@Override
	protected LivingThing createOffSpring(Location location) {
		Animal animal = createAnimal(false, field, location);
		diseases.forEach(disease -> {
			if (disease.affectsOffSpring()) {
				animal.getInfected(disease);
			}
		});
		return animal;
	}

	/**
	 * This is what the fox does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 * 
	 * @param field      The field currently occupied.
	 * @param newAnimals A list to return newly born foxes.
	 */
	@Override
	public void act(TimeOfDay timeOfDay, Weather weather, List<LivingThing> newAnimals) {
		/*
		 * Increment Age Increment Hunger Reproduce Find Food Eat Food Move
		 */

		spreadDiseases();
		applyDiseases();
		incrementAge();
		incrementHunger();

		if (!isAlive())
			return;

		Location newLocation = null;

		reproduce(newAnimals);

		Food food = findFood();

		if (!(food == null))
			newLocation = food.getLocation();
		// Move towards a source of food if found.
		if (canMove(timeOfDay, weather)) {
			move(newLocation);
		}
	}

	public boolean canMove(TimeOfDay timeOfDay, Weather weather) {
		return true;
	}

	/**
	 * Increase the age. This could result in the fox's death.
	 */
	protected void incrementAge() {
		age++;
		if (age > getMaxAge()) {
			setDead();
		}
	}

	/**
	 * Make this fox more hungry. This could result in the fox's death.
	 */
	protected void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			setDead();

		}
	}

	protected abstract int getBreedingAge();

	/**
	 * A rabbit can breed if it has reached the breeding age.
	 * 
	 * @return true if the rabbit can breed, false otherwise.
	 */
	protected boolean canBreed() {
		if (age < getBreedingAge())
			return false;

		List<Object> adjacentObjects = field.adjacentObjects(location);
		Iterator<Object> iterator = adjacentObjects.iterator();

		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if (object == null)
				continue;

			Entity entity = (Entity) object;
			if (entity.getClass() == this.getClass()) {
				Gender thisGender = this.getGender();
				Gender otherGender = ((Animal) entity).getGender();
				if ((thisGender == Gender.FEMALE) && (otherGender == Gender.MALE)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void move(Location newLocation) {
		if (newLocation == null)
			newLocation = field.freeAdjacentLocation(location);
		if (newLocation == null) {
			setDead(); // Overcrowding.
		} else {
			setLocation(newLocation);
		}
	}

	/**
	 * Look for rabbits adjacent to the current location. Only the first live rabbit
	 * is eaten.
	 * 
	 * @return Where food was found, or null if it wasn't.
	 */
	protected Food findFood() {
		Field field = getField();
		// Adjacent locations
		List<Object> adjacentObjects = field.adjacentObjects(getLocation());
		// Iterate through adjacent locations
		Iterator<Object> it = adjacentObjects.iterator();
		while (it.hasNext()) {
			Object object = it.next(); // An adjacent location
			if (isFood(object))
				return (Food) object;
		}
		return null;
	}

	public abstract Class[] getFoodSources();

	protected boolean isFood(Object object) {
		if (object == null)
			return false;
		Class[] foodSources = getFoodSources();
		for (int i = 0; i < foodSources.length; i++) {
			if (foodSources[i] == object.getClass()) {
				Food food = (Food) object;
				eat(food);
				return true;
			}
		}
		return false;
	}

	public void spreadDiseases() {
		Location location = getLocation();
		if (location != null) {
			List<Object> adjacentObjects = field.adjacentObjects(location);
			Iterator<Object> iterator = adjacentObjects.iterator();

			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object == null)
					continue;

				Entity entity = (Entity) object;
				if (entity.getClass() == this.getClass()) {
					((Animal) entity).addDiseases(this.getDiseases());
					this.addDiseases(this.getDiseases());
				}
			}
		}
	}

	public Set<Disease> getDiseases() {
		return diseases;
	}

	public void addDiseases(Set<Disease> diseases) {
		diseases.addAll(diseases);
	}

	public void getInfected(Disease disease) {
		diseases.add(disease.newDiseaseInstance());
	}

	public void eat(Food food) {
		food.getEaten();
		foodLevel = food.getFoodValue();
	}

	public void getCuredCompletely() {
		diseases.clear();
	}

	public void getCured(Disease disease) {
		diseases.remove(disease);
	}

	protected void applyDiseases() {
		for (Disease disease : diseases) {
			disease.affect(this);
		}
	}

	protected abstract int getMaxAge();

}
