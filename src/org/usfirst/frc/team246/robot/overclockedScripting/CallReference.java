package org.usfirst.frc.team246.robot.overclockedScripting;

import java.util.Arrays;
import java.util.HashMap;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

public class CallReference {

	//contains all registered methods
	public static HashMap<String, MethodHolder> methods = new HashMap<>();
	
	//contains all registered motors
	public static HashMap<String, CANTalon246> motors = new HashMap<>();	
	
	//denotes the category of help which should be returned
	private enum HelpType {
		ALL, METHODS, MOTORS, OTHER
	}
	
	private enum MotorCommandType {
		LIST ("lists all registered motors"),
		SET_POWER ("runs motor at specified raw power"),
		SET_SPEED ("runs motor at speed if it is registered"),
		SET_POSITION ("sets the encoder or PID position of the motor"),
		GET_POWER ("gets the raw power of the motor"),
		GET_SPEED ("gets the speed of motor if it is registered"),
		GET_POSITION ("gets the encoder or PID position of the motor"),
		GET_CURRENT ("gets the current in the motor"),
		INVALID ("");
		
		private final String d;
		
		private MotorCommandType(String s) {
			d = s;
		}
	}
	
	private enum MethodCommandType {
		LIST ("lists all registered methods"),
		METHOD_NAME ("executes the specified method with given arguments"),
		INVALID ("");
		
		private final String d;
		
		private MethodCommandType(String s) {
			d = s;
		}
	}
	
	
	//possible commands for the type all
	private final String[] ALL_DESCRIPTIONS = {
		"list-- lists all registered methods and motors"
	};
	
	/**
	 * Handles the given parsed arguments and calls the appropriate auxiliary
	 * handler method.  
	 *
	 * @param args the String[] in parsed formed
	 * @return the string containing the output of the command
	 */
	public String handle(String[] args) {
		
		//checks the first argument with no whitespace and 
		//calls the appropriate handler method
		switch(args[0]) {
		
			case "all": {
				return handleAll(args);
			}
			case "methods": {
				return handleMethod(args);
			}
			case "motors": {
				return handleMotor(args);
			}
			default: {
				return handleOther(args);
			}
		}
	}
	
	
	/**
	 * Auxiliary handler method for commands of type "all"
	 *
	 * @param args the String[] in parsed formed
	 * @return the string containing the output of the command
	 */
	private String handleAll(String[] args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument is "all"
		if(args.length == 1) {
			
			out += listPossibleCommands(HelpType.ALL);
		
		//arguments are "all" and "list"
		} else if(args[1].equals("list")) {

			out += listRegistered(HelpType.ALL);
		
		//argument is unlisted
		} else {

			out += "Command not found";

		}
		
		return out;
	}
	
	
	/**
	 * Auxiliary handler method for commands of type "method"
	 *
	 * @param args the String[] in parsed formed
	 * @return the string containing the output of the command
	 */
	private String handleMethod(String[] args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument is "methods"
		if(args.length == 1)  {
			out += listPossibleCommands(HelpType.METHODS);
		} else {
			switch(findMethodOperation(args)) {
				case LIST: {
					out += listRegistered(HelpType.METHODS);
					break;
				}
				case METHOD_NAME: {
					//copy arguments starting from right after the method name to end of user call
					//ex. methods:sampleMethod:arg1:arg2 would give a String[] {arg1,arg2}
					String[] params = new String[args.length - 2];
					for(int i = 0; i < params.length; i++) {
						params[i] = args[i+2];
					}
					
					//call method and return confirmation
					try {
						methods.get(args[1]).callMethod(params);
						out += "Method successfully called";
					
					//not enough arguments
					} catch(ArrayIndexOutOfBoundsException e) {
						out += "Missing required arguments for method, needs " +
								methods.get(args[1]).requiredParams();
					
					//unable to parse an argument to either a double or an int
					} catch(NumberFormatException e) {
						out += "Invalid numerical argument for method, needs " +
								methods.get(args[1]).requiredParams();
					}
				}
				case INVALID: {
					out += "Invalid argument. Method may not be registered or method command may not be valid."
							+ "\nTry \"methods:list\" for a list of registered methods or \"methods\" for a list of "
							+ "possible commands" + Arrays.toString(args);
					break;
				}
			}
		}
		
		return out;
	}
	
	private MethodCommandType findMethodOperation(String[] args) {
		if(methods.containsKey(args[1])) {
			return MethodCommandType.METHOD_NAME;
		} else {
			switch(args[1]) {
				case "list": {
					return MethodCommandType.LIST;
				}
				default: {
					return MethodCommandType.INVALID;
				}
			}
		}
	}
	
	
	/**
	 * Auxiliary handler method for commands of type "motor"
	 *
	 * @param args the String[] in parsed formed
	 * @return the string containing the output of the command
	 */
	private String handleMotor(String[] args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument was "motors"
		if(args.length == 1) {
			out += listPossibleCommands(HelpType.MOTORS);
		} else {
			switch(findMotorOperation(args)) {
				case LIST: {
					out += listRegistered(HelpType.MOTORS);
					break;
				}
				case SET_POWER: {
					CANTalon246 motor = motors.get(args[1]);
					//if the second argument is a valid integer, set motor at speed
					try {
						out += "setting speed " + Arrays.toString(args) + " " + args[3] + "\n";
						motor.set(Double.parseDouble(args[3]));
						out += "Set " + args[1] + " at " + Double.parseDouble(args[3]);
					//if not a valid integer, check for other arguments
					} catch (NumberFormatException e) {
						out += "Entered an invalid double as power, try again.";
					}
					break;
				}
				case GET_POWER: {
					out += args[1] + " is at speed: " + motors.get(args[1]).get();
					break;
				}
				case SET_SPEED: {
					CANTalon246 motor = motors.get(args[1]);
					//if the second argument is a valid integer, set motor at speed
					try {
						out += "setting speed " + Arrays.toString(args) + " " + args[3] + "\n";
						motor.scaledSet(Double.parseDouble(args[3]));
						out += "Set " + args[1] + " at " + Double.parseDouble(args[3]);
					//if not a valid integer, check for other arguments
					} catch (NumberFormatException e) {
						out += "Entered an invalid double as power, try again.";
					}
					break;
				}
				case GET_SPEED: {
					out += args[1] + " is at speed: " + motors.get(args[1]).getScaledSpeed();
					break;
				}
				case SET_POSITION: {
					try {
						motors.get(args[1]).setScaledPosition(Double.parseDouble(args[2]));
						out += "Set scalepoint of " + args[1] + " at " + Double.parseDouble(args[2]);
					} catch (NumberFormatException e) {
						out += "Entered an invalid double, try again.";
					}
					break;
				}
				case GET_POSITION: {
					out += args[1] + " is at position: " + motors.get(args[1]).getScaledPosition();
					break;
				}
				case GET_CURRENT: {
					out += args[1] + " is at current: " + motors.get(args[1]).getOutputCurrent();
					break;
				}
				case INVALID: {
					out += "Invalid argument. Motor may not be registered or motor command may not be valid."
							+ "\nTry \"motors:list\" for a list of registered motors or \"motors\" for a list of "
							+ "possible commands" + Arrays.toString(args);
					break;
				}
			}
		}
		
		
		return out;
	}
	
	private MotorCommandType findMotorOperation(String[] args) {
		if(args[1].equals("list")) {	
			return MotorCommandType.LIST;
		} else if(motors.containsKey(args[1]) && args.length > 1) {
			switch(args[2]) {
				case "setPower": {
					return MotorCommandType.SET_POWER;
				}
				case "getPower": {
					return MotorCommandType.GET_POWER;
				}
				case "setSpeed": {
					return MotorCommandType.SET_SPEED;
				}
				case "getSpeed": {
					return MotorCommandType.GET_SPEED;
				}
				case "setPosition": {
					return MotorCommandType.SET_POSITION;
				}
				case "getPosition": {
					return MotorCommandType.GET_POSITION;
				}
				case "getCurrent": {
					return MotorCommandType.GET_CURRENT;
				}
				default: {
					return MotorCommandType.INVALID;
				}
			}
		} else {
			return MotorCommandType.INVALID;
		}
	}
	
	/**
	 * Lists the possible commands of the given command type- all, method, motor, other.
	 *
	 * @param h the enumerated help type
	 * @return the string containing the list of possible commands of h
	 */
	private String listPossibleCommands(HelpType h) {
		
		String out = "";
		
		switch(h) {
			case ALL: {
				out += "Possible commands for type all:\n";
				
				for(String d : ALL_DESCRIPTIONS) {
					out += d + "\n";
				}
				
				return out;
			}
			case METHODS: {
				out += "Possible commands for type method:\n";
				
				for(MethodCommandType m : MethodCommandType.values()) {
					if(m.d != "") {
						out += "\n" + m.name() + "-- " + m.d;
					}
				}
				
				return out;
			}
			case MOTORS: {
				out += "Possible commands for type motor:\n";
				
				for(MotorCommandType m : MotorCommandType.values()) {
					if(m.d != "") {
						out += "\n" + m.name() + "-- " + m.d;
					}
				}
				
				return out;
			}
			default: {
				return "";
			}
		}
	}
	
	/**
	 * Lists the registered methods/motors of the given command type- all, method, motor, other.
	 * If "all" simply returns both methods and motor lists.
	 *
	 * @param h the enumerated help type
	 * @return the string containing the list of registered methods/motors
	 */
	private String listRegistered(HelpType h) {
		
		String out = "";
		
		switch(h) {
			case ALL: {
				
				out += "Methods\n";
				for(String key : methods.keySet()) {
					out += key + "\n";
				}
				out += "\nMotors\n";
				for(String key : motors.keySet()) {
					out += key + "\n";
				}
			
				return out;
				
			}
			case METHODS: {
	
				for(String key : methods.keySet()) {
					out += key + "\n";
				}
				
				return out;
			}
			case MOTORS: {
				
				for(String key : motors.keySet()) {
					out += key + "\n";
				}
			
				return out;
			}
			default: {
				
				return "";
			}
		}
	}
	
	//etc
	private String handleOther(String[] args) {
		return ("others: " +  Arrays.toString(args));
	}
	
	/**
	 * Adds the method to the Hashmap.
	 *
	 * @param name the name of the method to be added
	 * @param method the MethodHolder object which wraps the target method
	 */
	public static void addMethod(String name, MethodHolder method) {
		methods.put(name, method);
	}
	
	/**
	 * Adds the motor to the Hashmap
	 *
	 * @param name the name of the motor to be added
	 * @param motor the CANTalon246 reference to the target motor
	 */
	public static void addMotor(String name, CANTalon246 motor) {
		motors.put(name, motor);
	}
	
}