package environment.livingthings.animals.components.diseases;

import environment.livingthings.animals.Animal;

/**
 * This is a class representing Diseases in this simulation Diseases are
 * obtained by animals and affect them in different ways.
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public abstract class Disease {
/**
 * A method describing how a disease will impact an animal
 * @param animal to be impacted by the disease
 */
	public abstract void affect(Animal animal);
/**
 * Create a new instance of the same disease
 * @return
 */
	public abstract Disease newDiseaseInstance();
/**
 * Specify whether or not a disease spreads upon birth
 * @return true
 */
	public boolean affectsOffSpring() {
		return true;
	}

}
