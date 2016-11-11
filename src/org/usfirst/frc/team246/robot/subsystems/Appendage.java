package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Appendage extends Subsystem {

	public Appendage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public void motorsOn() {
		RobotMap.appendage1.set(0.7);
		RobotMap.appendage2.set(0.7);
	}
	
	public void motorsOff() {
		RobotMap.appendage1.set(0);
		RobotMap.appendage2.set(0);
	}

}
