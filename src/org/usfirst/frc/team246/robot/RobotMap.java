package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
// Electricity Monitoring Constants TODO garbage values :(
	public static final double MINIMUM_SAFE_VOLTAGE = 11;
	public static final double MINIMUM_SLIGHT_DANGER_VOLTAGE = 10;
	public static final double MINIMUM_MODERATE_DANGER_VOLTAGE = 9;
	public static final double MINIMUM_SEVERE_DANGER_VOLTAGE = 8;
	
// Testbed Motors (for electricity management)
	public static CANTalon246 unimportant;
	public static CANTalon246 appendage1;
	public static CANTalon246 appendage2;
	public static CANTalon246 drivetrain1;
	public static CANTalon246 drivetrain2;
	
	public static void init() {
		
		// Instantiate and configure objects here
		
	}
}
