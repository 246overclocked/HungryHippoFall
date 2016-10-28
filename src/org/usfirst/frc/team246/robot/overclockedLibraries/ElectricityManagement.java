package org.usfirst.frc.team246.robot.overclockedLibraries;

/**
 * 
 */
public abstract class ElectricityManagement implements Runnable {
	/** The diagnostic output instance. */
	private static ElectricityManagement globalInstance;

	/**
	 * Gets the single instance of ElectricityManagement.
	 *
	 * @return single instance of ElectricityManagement
	 */
	public static ElectricityManagement getInstance() {
		return globalInstance;
	}

	/**
	 * Initializes the diagnostics instance.
	 */
	public static void initialize(ElectricityManagement instance) {
		if (instance != null) {
			globalInstance = instance;
			(new Thread(instance)).start();
		}
	}
	
	enum DangerLevel {
		SAFE(0), SLIGHT(1), MODERATE(2), SEVERE(3), EXTREME(4);
		
		private double value;
		
		private DangerLevel(double value) {
			this.value = value;
		}
		
		public double getValue() {
			return value;
		}
	}
	
	public DangerLevel overallDangerLevel;
	public DangerLevel voltageDangerLevel;
	public DangerLevel currentDangerLevel;
	
	public abstract void setVoltageDangerLevel();
	
	public abstract void setCurrentDangerLevel();
	
	public void setOverallDangerLevel() {
		if (voltageDangerLevel.getValue() > currentDangerLevel.getValue()) {
			overallDangerLevel = voltageDangerLevel;
		}
		else {
			overallDangerLevel = currentDangerLevel;
		}	
	}
	
	public abstract void restoreSystems();	
	public abstract void cutNonCriticalSystems();	
	public abstract void cutBackAllModerate();	
	public abstract void cutBackAllSevere();	
	public abstract void cutBackAllExtreme();
	
	public void run() {
		while(true) {
			setVoltageDangerLevel();
			setCurrentDangerLevel();
			setOverallDangerLevel();
			
			switch (overallDangerLevel) {
				case SAFE:
					restoreSystems();
					break;
				case SLIGHT:
					cutNonCriticalSystems();
					break;
				case MODERATE:
					cutBackAllModerate();
					break;
				case SEVERE:
					cutBackAllSevere();
					break;
				case EXTREME:
					cutBackAllExtreme();
					break;
			}
		}
	}
}
