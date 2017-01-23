package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
	public static CANTalon246 testMotor1 = CANTalon246.init(2, 10);
	public static CANTalon246 testMotor2 = CANTalon246.init(3, 10);
	public static CANTalon246 testMotor3 = CANTalon246.init(4, 10);
	
	
	public static void init() {
		
		// Instantiate and configure objects here
		
	}
}
