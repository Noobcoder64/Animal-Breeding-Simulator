package simulator;

import java.util.List;

import environment.Environment;
import environment.livingthings.LivingThing;
import environment.livingthings.animals.Cerberus;
import environment.livingthings.animals.Phoenix;
import environment.livingthings.animals.Serpent;
import environment.livingthings.animals.Unicorn;
import environment.livingthings.animals.WereWolf;
import environment.livingthings.plants.Plant;
import simulator.field.Field;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field containing
 * multiple animals
 * 
 * @author Amit Setty (k1923164), Fahim Ahmed (k1921959), David J. Barnes and
 *         Michael Kölling
 * @version 2019.02.20
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.c
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    private static final int STEP_MINUTE_VALUE = 10;

    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    private Environment environment;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        field = new Field(depth, width);
        environment = new Environment();

        environment.populate(field);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Phoenix.class, Color.RED);
        view.setColor(Serpent.class, Color.MAGENTA);
        view.setColor(WereWolf.class, Color.GRAY);
        view.setColor(Cerberus.class, Color.BLACK);
        view.setColor(Unicorn.class, Color.CYAN);
        view.setColor(Plant.class, Color.GREEN);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period, (4000
     * steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps. Stop
     * before the given number of steps if it ceases to be viable.
     * 
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60); // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over the
     * whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        environment.getTime().increaseTimeByMinutes(STEP_MINUTE_VALUE);

        environment.update(STEP_MINUTE_VALUE);

        List<LivingThing> livingThings = environment.getLivingThings();
        List<LivingThing> newLivingThings = new ArrayList<>();

        // Let all rabbits act.
        for (Iterator<LivingThing> it = livingThings.iterator(); it.hasNext();) {
            LivingThing livingThing = it.next();
            livingThing.act(environment.getTime().getTimeOfDay(), environment.getWeather(), newLivingThings);
            if (!livingThing.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        livingThings.addAll(newLivingThings);

        view.showStatus(step, environment.getTime(), environment.getWeather(), field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        environment.getLivingThings().clear();
        environment.populate(field);

        // Show the starting state in the view.
        view.showStatus(step, environment.getTime(), environment.getWeather(), field);
    }

    /**
     * Pause for a given time.
     * 
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator(100, 200);
        simulator.runLongSimulation();
    }
}
