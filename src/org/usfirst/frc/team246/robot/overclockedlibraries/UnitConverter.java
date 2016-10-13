package org.usfirst.frc.team246.robot.overclockedLibraries;

public class UnitConverter {

	public UnitConverter() {
	}
	
	/**
	 * Converts from units/second to units/100ms
	 * 
	 * @param value
	 *            The value in units per second
	 * @return The value in units per 100 milliseconds
	 */
	public static double PerSecondToPer100ms(double value) {
		return value / 10;
	}

	/**
	 * Converts from units/100ms to units/second
	 * 
	 * @param value
	 *            The value in units per 100ms
	 * @return The value in units per second
	 */
	public static double Per100msToPerSecond(double value) {
		return value * 10;
	}

	/**
	 * Converts from units/second to units/10ms
	 * 
	 * @param value
	 *            The value in units per second
	 * @return The value in units per 10 milliseconds
	 */
	public static double PerSecondToPer10ms(double value) {
		return value / 100;
	}
	
	/**
	 * Converts from units/10ms to units/second
	 * 
	 * @param value
	 *            The value in units per 10ms
	 * @return The value in units per second
	 */
	public static double Per10msToPerSecond(double value) {
		return value * 100;
	}

	/**
	 * Converts from units/second to units/minute.
	 *
	 * @param value
	 *            The value in units per second
	 * @return The value in units mer minute
	 */
	public static double PerSecondToPerMinute(double value) {
		return value * 60;
	}

	/**
	 * Converts from units/minute to units/second.
	 *
	 * @param value
	 *            The value in units per minute
	 * @return The value in units per second
	 */
	public static double PerMinuteToPerSecond(double value) {
		return value / 60;
	}
}