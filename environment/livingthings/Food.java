package environment.livingthings;

import simulator.field.entity.Location;

/**
 * An interface representing food in this simulation
 * 
 * An object which is food can be eaten by animals in this simulation This
 * object must be able to die and must have a location This object also must
 * have a food value
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public interface Food {

	/**
	 * Code for when Food is eaten
	 */
	default void getEaten() {
		this.setDead();
	}

	/**
	 * 
	 * @return food value - describes the nutrition of food (how much it fills up
	 *         the thing that eats it)
	 */
	int getFoodValue();

	/**
	 * @return the location of the food
	 */
	Location getLocation();

	void setDead();
}
