package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

	public Drivetrain() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public static void drive(double speed1, double speed2) {
		RobotMap.drivetrain1.set(speed1);
		RobotMap.drivetrain2.set(speed2);
	}
	
	public static void stop() {
		RobotMap.drivetrain1.set(0);
		RobotMap.drivetrain2.set(0);
	}
}

