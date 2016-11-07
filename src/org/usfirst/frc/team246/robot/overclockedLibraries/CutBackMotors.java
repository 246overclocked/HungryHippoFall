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
		// TODO Auto-generated method stub

	}

	@Override
	public void cutBackAllSevere() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cutBackAllExtreme() {
		// TODO Auto-generated method stub

	}
	
	// Methods for doing things to motors
	
	private void cutUnimportant() {
		//turn off that motor
	}
	
	private void cutAppendage() {
		//turn off those motors
	}
	
	private void cutDrivetrain() {
		// scale back the voltage but don't shut it down
	}
	
	private void restoreAppendage(){
		//turn appendage back on??
	}
	
	private void restoreDrivetrain() {
		// scale back up to full power
	}

}
