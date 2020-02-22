package environment.livingthings.animals.components.diseases;

import java.util.Random;

import environment.livingthings.animals.Animal;

/**
 * This is a class representing Rabies in this simulation Rabies is a type of
 * disease has a chance in killing an animal afflicted with it every step of the
 * simulation
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public class Rabies extends Disease {
	Random rand = new Random();
/**
 * {@inheritDoc}
 * By chance might kill the animal every time this method is launched
 */
	@Override
	public void affect(Animal animal) {
		if (rand.nextDouble() <= .15)
			animal.setDead();
	}
/**
 * Specify whether or not a disease spreads upon birth 
 * @return false
 */
	@Override
	public boolean affectsOffSpring() {
		return false;
	}

	@Override
	public Disease newDiseaseInstance() {
		return new Rabies();
	}

}
