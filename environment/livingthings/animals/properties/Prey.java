package environment.livingthings.animals.properties;

import environment.livingthings.Food;

/**
 * This is the Prey interface. It is created to help with future implementation
 * of what a Prey must do in this application. At the moment all Preys have a
 * method which checks whether they are alive but this should be extended in the
 * future.
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) (k1923164)
 * @version 2019.20.2
 */
public interface Prey extends Food {
	
	/**
	 * Return whether a prey is alive.
	 * 
	 * @return true if the predator is alive.
	 */
	boolean isAlive();
}
