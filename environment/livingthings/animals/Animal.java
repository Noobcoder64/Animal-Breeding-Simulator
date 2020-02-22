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

	// The animal's age.
	protected int age;
	// The animal's food level, which is increased by eating 
	protected int foodLevel;

	// The animal's gender.
	protected Gender gender;

	// The diseases that an animal carries.
	protected Set<Disease> diseases;

	/**
	 * Create a new animal at location in field. An animal may be created with age zero (a new born) or
	 * with a random age. An animal may be Male or Female.
	 * 
	 * @param randomAge
	 *            Determine whether the animal has a random age.
	 * @param field
	 *            The field currently occupied.
	 * @param location
	 *            The location within the field.
	 */
	public Animal(boolean randomAge, Field field, Location location) {
		super(field, location);

		if (randomAge) {
			age = rand.nextInt(getMaxAge());
		} else {
			age = 0;
		}

		foodLevel = getInitialFoodLevel();
		
		// Determine whether the animal will be a MALE or a FEMALE.
		gender = rand.nextBoolean() ? Gender.MALE : Gender.FEMALE;

		diseases = new HashSet<>();
	}


	/**
	 * Return the gender of an animal.
	 * 
	 * @return the gender of an animal.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Create a new animal at location in field.
	 * 
	 * @param randomAge
	 *            Determine whether the animal has a random age.
	 * @param field
	 *            The field currently occupied.
	 * @param location
	 *            The location within the field.
	 */
	abstract Animal createAnimal(boolean randomAge, Field field, Location location);

	/**
	 * Create a new animal at location in field and infect the offspring if the
	 * disease is spreadable.
	 * 
	 * @param location
	 *            The location within the field.
	 */
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
	 * This is what the animal does most of the time: it seeks for food. In the
	 * process, it might breed, die for diseases, die of hunger, die of old age and
	 * finally move. The actions of an animal depend on the time of the day and the
	 * weather.
	 * 
	 * @param timeOfDay
	 *            The current time of the day.
	 * @param weather
	 *            The current weather conditions.
	 * @param field
	 *            The field currently occupied.
	 * @param newAnimals
	 *            A list to return newly born Animals
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

	/**
	 * Return whether an animal can move when acting.
	 * 
	 * @param timeOfDay
	 *            The current time of the day.
	 * @param weather
	 *            The current weather conditions.
	 * 
	 * @return true if an animal can move.
	 */

	public boolean canMove(TimeOfDay timeOfDay, Weather weather) {
		return true;
	}

	/**
	 * Increase the age. This could result in the animal's death.
	 */
	protected void incrementAge() {
		age++;
		if (age > getMaxAge()) {
			setDead();
		}
	}

	/**
	 * Make this animal more hungry. This could result in the animal's death.
	 */
	protected void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			setDead();

		}
	}

	/**
	 * Return the breeding age of an animal.
	 * 
	 * @return the breeding age of an animal.
	 */
	protected abstract int getBreedingAge();

	/**
	 * Return whether an animal can breed. By default, An animal can breed if it has
	 * reached the breeding age and met an adjacent animal of the same species with
	 * opposite gender.
	 * 
	 * @return true if the animal can breed.
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

	/**
	 * Move to the location specified. Move to a random location if the givent
	 * location is null. Die for overcrowding if no adjacent location is available.
	 * 
	 * @param newLocation
	 *            The location in which the animal should move.
	 */
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
	 * Look for food in the adjacent locations to the current location.
	 * 
	 * @return food if found, null otherwise.
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

	/**
	 * Return the food sources of an animal (classes which an animal can 'eat').
	 * 
	 * @return  the food sources of an animal.
	 */
	public abstract Class[] getFoodSources();

	/**
	 * Determine whether the given object is a food source of an animal.
	 * 
	 * @param object
	 *            Object to be inspected.
	 * @return true if the object is a food source.
	 */
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

	/**
	 * Spread the diseases carried by an animal to other animals.
	 * 
	 */
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

	/**
	 * Return the diseases carried by an animal
	 * 
	 * @return the diseases carried by an animal
	 */
	public Set<Disease> getDiseases() {
		return diseases;
	}

	/**
	 * Infect an animal with multiple diseases.
	 * 
	 * @param diseases
	 *            Diseases which will infect the animal.
	 */
	public void addDiseases(Set<Disease> diseases) {
		diseases.addAll(diseases);
	}

	/**
	 * Infect an animal a disease.
	 * 
	 * @param diseases
	 *            Disease which will infect the animal.
	 */
	public void getInfected(Disease disease) {
		diseases.add(disease.newDiseaseInstance());
	}

	/**
	 * Eat the specified food and make this animal less hungry.
	 * 
	 * @param food
	 *            The food to be eaten.
	 */
	public void eat(Food food) {
		food.getEaten();
		foodLevel = food.getFoodValue();
	}

	/**
	 * Cure an animal from all the diseases
	 */
	public void getCuredCompletely() {
		diseases.clear();
	}

	/**
	 * Cure an animal from one disease
	 * 
	 * @param disease
	 */
	public void getCured(Disease disease) {
		diseases.remove(disease);
	}

	/**
	 * Allow the diseases to take effect when an animal is acting.
	 * 
	 */
	protected void applyDiseases() {
		for (Disease disease : diseases) {
			disease.affect(this);
		}
	}

	/**
	 * Return the maximum age of an animal.
	 * 
	 * @return the maximum age of an animal.
	 */
	protected abstract int getMaxAge();
	
	/**
	 * Return the initial food level of an animal.
	 * 
	 * @return the initial food level of an animal.
	 */
	protected abstract int getInitialFoodLevel();


}
