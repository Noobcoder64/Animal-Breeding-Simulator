package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import environment.livingthings.LivingThing;
import environment.livingthings.animals.Cerberus;
import environment.livingthings.animals.Phoenix;
import environment.livingthings.animals.Serpent;
import environment.livingthings.animals.Unicorn;
import environment.livingthings.animals.WereWolf;
import environment.livingthings.animals.components.diseases.HealableVirus;
import environment.livingthings.animals.components.diseases.Rabies;
import environment.livingthings.animals.components.diseases.Virus;
import environment.livingthings.plants.Plant;
import environment.weather.Condition;
import environment.weather.TimeStorage;
import environment.weather.Weather;
import simulator.Randomizer;
import simulator.field.Field;
import simulator.field.entity.Location;

/**
 * A class representing the environment of the simulation
 * The Environment of the simulation sets up the simulation adding all entities, 
 * diseases, time, and weather to the simulation. 
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */


public class Environment {
    // The probability that a werewolf will be created in any given grid position.
    private static final double WEREWOLF_CREATION_PROBABILITY = 0.08;
 // The probability that a serpent will be created in any given grid position.
    private static final double SERPENT_CREATION_PROBABILITY = 0.08;
 // The probability that a cerberus will be created in any given grid position.
    private static final double CERBERUS_CREATION_PROBABILITY = 0.08;
    // The probability that a phoenix will be created in any given grid position.
    private static final double PHOENIX_CREATION_PROBABILITY = 0.28;
    // The probability that a unicorn will be created in any given grid position.
    private static final double UNICORN_CREATION_PROBABILITY = 0.15;
    // The probability that a plant will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.05;
 // The probability that a virus will be created on an animal
    private static final double VIRUS_CREATION_PROBABILITY = 0.05;
// The probability that a healable virus will be created on an animal
    private static final double HEALABLE_VIRUS_CREATION_PROBABILITY = 0.05;

    private Weather weather;

    private TimeStorage time;

    private List<LivingThing> livingThings;
/**
 * This is a default constructor which sets up the environment object
 * It creates an empty ArrayList for the living things, creates time for the simulation, and creates a weather object. 
 */
    public Environment() {
        livingThings = new ArrayList<>();
        time = new TimeStorage();
        weather = new Weather(Condition.SUNNY, false);
    }

   /**
    * This method populates the field with entities to allow them to add them to the simulation
    * @param field
    */
    public void populate(Field field) {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                
                Location location = new Location(row, col);
                if (rand.nextDouble() <= WEREWOLF_CREATION_PROBABILITY) {
                    WereWolf wereWolf = new WereWolf(true, field, location);
                    livingThings.add(wereWolf);
                    if (rand.nextDouble() <= VIRUS_CREATION_PROBABILITY) {
                        wereWolf.getInfected(new Virus());
                    }

                } else if (rand.nextDouble() <= PHOENIX_CREATION_PROBABILITY) {
                
                    Phoenix phoenix = new Phoenix(true, field, location);
                    livingThings.add(phoenix);
                    if (rand.nextDouble() <= VIRUS_CREATION_PROBABILITY) {
                        phoenix.getInfected(new Virus());
                    }
                } else if (rand.nextDouble() <= PLANT_CREATION_PROBABILITY) {
                 
                    Plant plant = new Plant(field, location);
                    livingThings.add(plant);
                } else if (rand.nextDouble() <= SERPENT_CREATION_PROBABILITY) {
          
                    Serpent serpent = new Serpent(true, field, location);
                    livingThings.add(serpent);
                    if (rand.nextDouble() <= VIRUS_CREATION_PROBABILITY) {
                        serpent.getInfected(new Virus());
                    }
                    if (rand.nextDouble() <= HEALABLE_VIRUS_CREATION_PROBABILITY) {
                        serpent.getInfected(new HealableVirus());
                    }
                } else if (rand.nextDouble() <= CERBERUS_CREATION_PROBABILITY) {
               
                    Cerberus cerberus = new Cerberus(true, field, location);
                    livingThings.add(cerberus);
                    cerberus.getInfected(new Rabies()); // all Cerberus carry Rabies
                    if (rand.nextDouble() <= VIRUS_CREATION_PROBABILITY) {
                        cerberus.getInfected(new Virus());
                    }
                } else if (rand.nextDouble() <= UNICORN_CREATION_PROBABILITY) {
         
                    Unicorn unicorn = new Unicorn(true, field, location);
                    livingThings.add(unicorn);
                    unicorn.getInfected(new Rabies()); // all Unicorn carry Rabies
                    if (rand.nextDouble() <= VIRUS_CREATION_PROBABILITY) {
                        unicorn.getInfected(new Virus());
                    }

                }
                // else leave the location empty.
            }
        }
    }
/**
 * Gets a list of all Living things
 * @return livingThings
 */
    public List<LivingThing> getLivingThings() {
        return livingThings;
    }
/**
 * Gets the time in the environment
 * @return time
 */
    public TimeStorage getTime() {
        return time;
    }
/**
 * Gets the weather for the environment
 * @return weather
 */
    public Weather getWeather() {
        return weather;
    }
/**
 * Updates the time and weather in the simulation
 * @param stepMinuteValue - amount of minutes to increase time by 
 */
    public void update(int stepMinuteValue) {
        if (time.compare(weather.getStopTime()) == 1) {
            weather.change();
        }

        time.increaseTimeByMinutes(stepMinuteValue);

    }
}
