package org.usfirst.frc.team246.robot.overclockedScripting;

/**
 * An anonymous in-line class used to wrap methods in order to store them in the CallReference.
 */
public abstract class MethodHolder {
	
	public String name;
	
	/**
	 * Wraps, calls, and handles the arguments for the target method
	 *
	 * @param args the user-specified arguments for the target method
	 * @throws ArrayIndexOutOfBoundsException if there are not enough
	 * arguments to call the wrapped method
	 * @throws NumberFormatException the number format exception if the
	 * given arguments could not be cast to the appropriate type
	 */
	public abstract void callMethod(String[] args) throws ArrayIndexOutOfBoundsException, 
															NumberFormatException;

	/**
	 * Returns a list of required parameters for the wrapped method.
	 * 
	 * When returning the string with the parameter types, return in the format
	 * "{Type}, {Type}, ..." where {Type} is the primitive or object name as seen
	 * in Java, ex. "double, CANTalon246, String" 
	 *
	 * @return the string with the required parameters for the wrapped methods.
	 */
	public abstract String requiredParams();
}
