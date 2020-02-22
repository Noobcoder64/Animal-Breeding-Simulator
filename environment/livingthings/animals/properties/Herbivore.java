package environment.livingthings.animals.properties;
import environment.livingthings.Food;
/**
 * This is the Herbivore interface. It is created to help with future implementation of what a herbivore
 * must do in this application. At the moment all Herbivores  must eat food but this should be extended in the future. 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164) (k1923164)
 * @version 2019.20.2
 */
public interface Herbivore {
	/**
	 * Method for a Herbivore to eat food.
	 * @param food
	 */
	void eat(Food food);
}
