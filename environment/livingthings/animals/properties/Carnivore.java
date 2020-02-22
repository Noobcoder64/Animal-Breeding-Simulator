/**
 * This is the Carnivore interface. It is created to help with future implementation of what a carnivore
 * must do in this application. At the moment all Carnivores must eat food but this should be extended in the future. 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) (k1923164)
 * @version 2019.20.2
 */

package environment.livingthings.animals.properties;
import environment.livingthings.Food;
/**
 * This is the Carnivore interface. It is created to help with future implementation of what a Carnivore
 * must do in this application. At the moment all Carnivores  must eat food but this should be extended in the future. 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) (k1923164)
 * @version 2019.20.2
 */
public interface Carnivore {
	/**
	 * Method for a Carnivore to eat food
	 * @param food
	 */
	void eat(Food food);
}
