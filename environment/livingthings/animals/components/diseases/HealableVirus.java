package environment.livingthings.animals.components.diseases;

import java.util.Random;

import environment.livingthings.animals.Animal;

/**
 * This is a class representing HealableVirus in this simulation HealableVirus
 * is a type of disease every step has a chance in killing an animal afflicted
 * with it for every step of the simulation. However, after a certain amount of
 * the time the animal will be healed from the virus
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public class HealableVirus extends Disease {
	Random rand = new Random();
	private int stepsWithVirus = 0;
/**
 * {@inheritDoc}
 * Will impact the animal by killing them if they obtained the disease in the last 10 steps and 
 * && if by chance
 */
	@Override
	public void affect(Animal animal) {
		if (stepsWithVirus < 10 && rand.nextDouble() <= .05) {
			animal.setDead();
		}
		else if(stepsWithVirus>=10){
			animal.getCured(this);
		}
		stepsWithVirus++;
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
		return new HealableVirus();
	}

}
