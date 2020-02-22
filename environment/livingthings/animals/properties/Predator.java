package environment.livingthings.animals.properties;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * This is the Predator interface. It is created to help with future implementation of what a predator
 * must do in this application. At the moment all Predators must compete. 
 * This should be extended in the future. 
 * A Predator is a Carnivore
 * 
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) (k1923164)
 * @version 2019.20.2
 */
public interface Predator extends Carnivore {
	Location getLocation();
	Field getField();
	Class[] getFoodSources();
/**
 * The compete method has a predator compete with any predators who eat the same food source as
 * him/her. When a predator competes they will kill the other predator if their strength is higher 
 * than or equal to theirs and they will die if their strength is higher than or equal to the other predators. 
 */
	default void compete() {
		List<Object> adjacentObjects;
		Class[] foodSources = getFoodSources();
		Location location = getLocation();
		Field field = getField();
		if (location != null) {
			adjacentObjects = field.adjacentObjects(location); // Iterate through adjacent locations
			Iterator<Object> it = adjacentObjects.iterator();
			while (it.hasNext()) {
				Object object = it.next(); // An adjacent location Object animal =

				if (object!=null&&object.getClass()==this.getClass())
					continue;

				if (object instanceof Predator) {
					Predator predator = (Predator) object;

					for (int i = 0; i < foodSources.length; i++) {
						List<Class> predatorFoodSources = Arrays.asList(predator.getFoodSources());
						if (predatorFoodSources.contains(foodSources[i])) {
							if (getStrength() > predator.getStrength()) {
								predator.setDead();
							} else if (getStrength() < predator.getStrength()) {
								setDead();
							} else {
								predator.setDead();
								setDead();
							}
						}
					}
				}
			}
		}
	}


	
/**
 * 
 * @return the strength of the predator
 */
	int getStrength();
/**
 * sets the predator to be dead
 */
	void setDead();


}
