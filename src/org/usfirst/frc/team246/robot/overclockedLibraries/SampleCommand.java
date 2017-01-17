package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedScripting.SampleSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SampleCommand extends Command{

	public SampleCommand() {
		requires(new SampleSubsystem());
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void execute() {
		RobotMap.testMotor1.set(0.1);
		RobotMap.testMotor2.set(0.1);
		RobotMap.testMotor3.set(0.1);
	}
	
	@Override
	public void end() {
		RobotMap.testMotor1.set(0);
		RobotMap.testMotor2.set(0);
		RobotMap.testMotor3.set(0);
	}
	
	@Override
	public void interrupted() {
		end();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	
}
