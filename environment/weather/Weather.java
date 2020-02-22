package environment.weather;
import java.util.Random;

import simulator.Randomizer;
/**
 * A class representing the weather of the simulation
 * The Weathert of the simulation gives the current 
 * weather condition and a boolean showing if it is raining or not
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */

public class Weather {
	private static final Random rand = Randomizer.getRandom();
	
	private static final int MAX_DAYS_DURATION = 0;
	private static final int MAX_HOURS_DURATION = 5;
	private static final int MAX_MINUTES_DURATION = 0;
	
	private Condition condition;
	private boolean isRaining;
	// private double temperature;
	private TimeStorage stopTime;
/**
 * Constructor creating a Weather object with a Condition and isRaining boolean
 * @param condition
 * @param isRaining
 */
	public Weather(Condition condition, boolean isRaining) {
		this.condition = condition;
		this.isRaining = isRaining;
		stopTime = new TimeStorage();
	}
	/**
	 * Sets the current time of the simulation to an inputted TimeStorage object
	 * @param timeStorage
	 */
	public void setCurrentTime(TimeStorage timeStorage) {
		stopTime.setTime(timeStorage);
	}
	/**
	 * Changes the weather randomly
	 */
	public void change() {
		setRandomCondition();
		setRandomDuration();
	}

	private void setRandomCondition() {
		Condition[] conditions = Condition.values();
		condition = conditions[rand.nextInt(conditions.length)];
		isRaining = rand.nextBoolean();
	}

	private void setRandomDuration() {
		stopTime.increaseTimeByDays(rand.nextInt(MAX_DAYS_DURATION + 1));
		stopTime.increaseTimeByHours(rand.nextInt(MAX_HOURS_DURATION + 1));
		stopTime.increaseTimeByMinutes(rand.nextInt(MAX_MINUTES_DURATION + 1));
	}
	/**
	 * Returns the time when the weather must change
	 * @return stopTime
	 */
	public TimeStorage getStopTime() {
		return stopTime;
	}
	/**
	 * 
	 * @return isRaining
	 */
	public boolean isRaining() {
		return isRaining;
	}
	
	@Override
	public String toString() {
		return condition.toString();
	}
	/**
	 * @return formatted string representing the weather condition
	 */
	public String getConditionString() {
		return condition.toString();
	}
/**
 * 
 * @return "yes" if it is raining, "no" if it is not raining
 */
	public String isRainingString() {
		return isRaining ? "Yes" : "No";
	}
}
