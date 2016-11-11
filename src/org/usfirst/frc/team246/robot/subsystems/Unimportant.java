package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.UnimportantOff;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Unimportant extends Subsystem {

	public Unimportant() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UnimportantOff());

	}
	
	public void motorOff() {
		RobotMap.unimportant.set(0);
	}
	
	public void motorOn() {
		RobotMap.unimportant.set(0.5);
	}

}
