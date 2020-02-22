package environment.livingthings.animals.components.diseases;

import environment.livingthings.animals.Animal;

/**
 * This is a class representing the Virus in this simulation A virus is a type
 * of disease which kills an animal which is afflicted with it after 4
 * simulation steps
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public class Virus extends Disease {
	private int stepsWithVirus = 0;
/**
 * {@inheritDoc}
 * Will kill the animal after this method is called 4 times. 
 * This should be used to keep track of the steps since the virus has been present on the animal
 */
	@Override
	public void affect(Animal animal) {
		if (stepsWithVirus > 4) {
			animal.setDead();
		}
		stepsWithVirus++;
	}

	@Override
	public Disease newDiseaseInstance() {
		return new Virus();
	}

}
