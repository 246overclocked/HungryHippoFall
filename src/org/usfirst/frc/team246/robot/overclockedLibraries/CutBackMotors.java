package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.RobotMap;

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
		RobotMap.unimportant.set(0); //TODO is this right?
	}
	
	private void cutAppendage() {
		RobotMap.appendage1.set(0);
		RobotMap.appendage2.set(0);
	}
	
	private void cutDrivetrain() {
		// scale back the voltage but don't shut it down
	}
	
	private void restoreUnimportant(){
		//turn appendage back on??
	}
	
	private void restoreAppendage(){
		//turn appendage back on??
	}
	
	private void restoreDrivetrain() {
		// scale back up to full power
	}

}
