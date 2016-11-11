package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.AppendageOff;
import org.usfirst.frc.team246.robot.commands.AppendageOn;
import org.usfirst.frc.team246.robot.commands.Drive;
import org.usfirst.frc.team246.robot.commands.StopDrivetrain;
import org.usfirst.frc.team246.robot.commands.UnimportantOff;
import org.usfirst.frc.team246.robot.commands.UnimportantOn;

public class CutBackMotors extends ElectricityManagement {

	public CutBackMotors() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setVoltageDangerLevel() {
		if (Diagnostics.getVoltageLeeway() > RobotMap.MINIMUM_SAFE_VOLTAGE) {
			voltageDangerLevel = DangerLevel.SAFE;		
		}
		else if (Diagnostics.getVoltageLeeway() > RobotMap.MINIMUM_SLIGHT_DANGER_VOLTAGE) {
			voltageDangerLevel = DangerLevel.SLIGHT;
		}
		else if (Diagnostics.getVoltageLeeway() > RobotMap.MINIMUM_MODERATE_DANGER_VOLTAGE) {
			voltageDangerLevel = DangerLevel.MODERATE;
		}
		else if (Diagnostics.getVoltageLeeway() > RobotMap.MINIMUM_SEVERE_DANGER_VOLTAGE) {
			voltageDangerLevel = DangerLevel.SEVERE;
		}
		else {
			voltageDangerLevel = DangerLevel.EXTREME;
		}
	}

	@Override
	public void setCurrentDangerLevel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void restoreSystems() {
		restoreUnimportant();
		restoreAppendage();
		restoreDrivetrain();
	}

	@Override
	public void cutNonCriticalSystems() {
		cutUnimportant();
		restoreAppendage();
		restoreDrivetrain();
	}

	@Override
	public void cutBackAllModerate() {
		cutUnimportant();
		cutAppendage();
		restoreDrivetrain();

	}

	@Override
	public void cutBackAllSevere() {
		cutUnimportant();
		cutAppendage();

	}

	@Override
	public void cutBackAllExtreme() {
		cutUnimportant();
		cutAppendage();
		cutDrivetrain();

	}
	
	// Methods for doing things to motors
	
	private void cutUnimportant() {
		(new UnimportantOff()).start();
	}
	
	private void cutAppendage() {
		(new AppendageOff()).start();
	}
	
	private void cutDrivetrain() {
		(new StopDrivetrain()).start();
	}
	
	private void restoreUnimportant(){
		(new UnimportantOn()).start();
	}
	
	private void restoreAppendage(){
		(new AppendageOn()).start();
	}
	
	private void restoreDrivetrain() {
		(new Drive()).start();
	}

}
