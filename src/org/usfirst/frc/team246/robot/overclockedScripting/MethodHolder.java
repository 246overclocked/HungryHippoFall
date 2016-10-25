package org.usfirst.frc.team246.robot.overclockedScripting;

public abstract class MethodHolder {
	
	public String name;
	
	public abstract void callMethod(String[] args) throws ArrayIndexOutOfBoundsException, 
															NumberFormatException;

	public abstract String requiredParams();
}
