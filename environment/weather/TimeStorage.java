package environment.weather;

import environment.time.TimeOfDay;

/**
 * A class representing the time in the simulation The TimeStorage object holds
 * the days, hours, and minutes since the begininning of the simulation and
 * updates them
 * 
 * @author Fahim Ahmed (k1921959), Amit Setty (k1923164)
 * @version 2019.02.20
 */
public class TimeStorage {
	private int days;
	private int hours;
	private int minutes;
	private TimeOfDay timeOfDay;

	/**
	 * A constructor creating a TimeStorage object at a specific time
	 * 
	 * @param days
	 * @param hours
	 * @param minutes
	 */
	public TimeStorage(int days, int hours, int minutes) {
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		updateTimeOfDay();
	}

	/**
	 * A default constructor creating a Time Storage object at 1 day, 0 hours, 0
	 * minutes (the start of a simulation)
	 */
	public TimeStorage() {
		this(1, 0, 0);
	}

	/**
	 * Increases the time in the simulation by x minutes
	 * 
	 * @param minutes
	 */
	public void increaseTimeByMinutes(int minutes) {
		if ((this.minutes + minutes) > 59) {
			increaseTimeByHours(1);
		}
		this.minutes = (this.minutes + minutes) % 60;
		updateTimeOfDay();
	}

	/**
	 * Increases the time in the simulation by x hours
	 * 
	 * @param hours
	 */
	public void increaseTimeByHours(int hours) {
		if ((this.hours + hours) > 23) {
			increaseTimeByDays(1);
		}
		this.hours = (this.hours + hours) % 24;
		updateTimeOfDay();
	}

	/**
	 * Increases the time in the simulation by x days
	 * 
	 * @param days
	 */
	public void increaseTimeByDays(int days) {
		this.days += days;
		updateTimeOfDay();
	}

	/**
	 * Updates the time of day (Morning/ Night) in the simulation depending on what
	 * the current hour in the day is
	 * 
	 * Night & Day each last 12 hours longs
	 */
	private void updateTimeOfDay() {
		if (hours >= 6 && hours < 18)
			timeOfDay = TimeOfDay.MORNING;
		if (hours < 6 || hours >= 18)
			timeOfDay = TimeOfDay.NIGHT;
	}

	/**
	 * Set the current time to a different time using a different TimeStorage object
	 * 
	 * @param timeStorage
	 */
	public void setTime(TimeStorage timeStorage) {
		this.days = timeStorage.days;
		this.hours = timeStorage.hours;
		this.minutes = timeStorage.minutes;
	}

	/**
	 * Set the current time of the simulation by inputting days, hours, and minutes
	 * 
	 * @param days
	 * @param hours
	 * @param minutes
	 */
	public void setTime(int days, int hours, int minutes) {
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}

	/**
	 * 
	 * @return timeOfDay
	 */
	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}

	@Override
	public String toString() {
		return "Day: " + days + " Time: " + hours + ":" + minutes;
	}

	/**
	 * Compares the inputted parameter to the current time.
	 * 
	 * @param stopTime
	 * @return 1 if this TimeStorage object has greater time value, 0 if equal, -1
	 *         if inputted parameter has greater TimeStorage value
	 * 
	 */
	public int compare(TimeStorage stopTime) {
		if (days > stopTime.days)
			return 1;
		if (days < stopTime.days)
			return -1;
		if (hours > stopTime.hours)
			return 1;
		if (hours < stopTime.hours)
			return -1;
		if (minutes > stopTime.minutes)
			return 1;
		if (minutes < stopTime.minutes)
			return -1;
		return 0;
	}

	/**
	 * 
	 * @return days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * 
	 * @return hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * 
	 * @return minutes
	 */
	public int getMinutes() {
		return minutes;
	}
}
