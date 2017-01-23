package org.usfirst.frc.team246.robot.overclockedScripting;

import java.util.Arrays;
import java.util.HashMap;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;
import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246.SensorType;
import org.usfirst.frc.team246.robot.overclockedScripting.RoboScripting.MethodHolder;

import edu.wpi.first.wpilibj.command.Command;

public class CallReference {

	/** Contains all registered methods. */
	public static HashMap<String, MethodHolder> methods = new HashMap<>();
	
	/** Contains all registered motors. */
	public static HashMap<String, CANTalon246> motors = new HashMap<>();	
	
	/** Contains all registered commands. */
	public static HashMap<String, Command> commands = new HashMap<>();
	
	//denotes the category of help which should be returned
	private enum HelpType {
		METHODS, MOTORS, COMMANDS, OTHER
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
	
	private enum MotorCommandType {
		LIST ("lists all registered motors"),
		SET_POWER ("runs motor at specified raw power"),
		SET_SPEED ("runs motor at specified scaled speed"),
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
	
	private enum CommandCommandType {
		LIST ("lists all registered commands"),
		RUN ("executes command"),
		STOP ("stops command"),
		INVALID ("");
		
		private final String d;
		
		private CommandCommandType(String s) {
			d = s;
		}
	}
	
	//anything user-inputed that does not fit into a predefined category
	private enum OtherCommandType {
		LIST ("lists all registered entries"),
		HELP ("lists all possible commands"),
		INVALID ("");
		
		private final String d;
		
		private OtherCommandType(String s) {
			d = s;
		}
	}

	
	/**
	 * Handles the given parsed arguments and calls the appropriate auxiliary
	 * handler method.  
	 *
	 * @param args the String[] of arguments passed from the user
	 * @return the string containing the output of the command
	 */
	public String handle(String[] args) {
		
		//checks the first argument with no whitespace and 
		//calls the appropriate handler method
		switch(args[0]) {
			case "methods": {
				return handleMethod(args);
			}
			case "motors": {
				return handleMotor(args);
			}
			case "commands": {
				return handleCommand(args);
			}
			default: {
				return handleOther(args);
			}
		}
	}
	
	/**
	 * Auxiliary handler method for commands of type "method"
	 *
	 * @param args the String[] of arguments passed from the user
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
					break;
				}
				case INVALID: {
					out += "Invalid argument. Method may not be registered or method command may not be valid."
							+ "\nTry \"methods:list\" for a list of registered methods or \"methods\" for a list of "
							+ "possible commands" + "\nArgs: " + Arrays.toString(args);
					break;
				}
			}
		}
		
		return out;
	}
	
	/**
	 * Handles classifying the needed method operation and returning the appropriate enum.
	 *
	 * @param args the String[] of arguments passed from the user 
	 * @return the appropriate MethodCommandType enum
	 */
	private MethodCommandType findMethodOperation(String[] args) {
		if(methods.containsKey(args[1])) {
			return MethodCommandType.METHOD_NAME;
		} else {
			switch(args[1].toLowerCase()) {
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
	 * @param args the String[] of arguments passed from the user
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
					try {
						out += "setting speed " + Arrays.toString(args) + " " + args[3] + "\n";
						motor.set(Double.parseDouble(args[3]));
						out += "Set " + args[1] + " at " + Double.parseDouble(args[3]);
					
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
					if(motor.getSensorType() != SensorType.OTHER) {
						try {
							out += "setting speed " + Arrays.toString(args) + " " + args[3] + "\n";
							motor.scaledSet(Double.parseDouble(args[3]));
							out += "Set " + args[1] + " at " + Double.parseDouble(args[3]);
						} catch (NumberFormatException e) {
							out += "Entered an invalid double as power, try again.";
						}
					} else {
						out += "Invalid command, no sensor found";
					}
					break;
				}
				case GET_SPEED: {
					CANTalon246 motor = motors.get(args[1]);
					if(motor.getSensorType() != SensorType.OTHER) {
						out += args[1] + " is at speed: " + motor.getScaledSpeed();
					} else {
						out += "Invalid command, no sensor found";
					}
					break;
				}
				case SET_POSITION: {
					try {
						motors.get(args[1]).setScaledPosition(Double.parseDouble(args[3]));
						out += "Set scalepoint of " + args[1] + " at " + Double.parseDouble(args[3]);
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
							+ "possible commands" + "\nArgs: " + Arrays.toString(args);
					break;
				}
			}
		}
		
		System.out.println("returning out");
		return out;
	}
	
	/**
	 * Handles classifying the needed motor operation and returning the appropriate enum.
	 *
	 * @param args the String[] of arguments passed from the user 
	 * @return the appropriate MotorCommandType enum
	 */
	private MotorCommandType findMotorOperation(String[] args) {
		if(args[1].equals("list")) {	
			return MotorCommandType.LIST;
		} else if(motors.containsKey(args[1]) && args.length > 2) {
			switch(args[2].toLowerCase()) {
				case "set_power": {
					return MotorCommandType.SET_POWER;
				}
				case "get_power": {
					return MotorCommandType.GET_POWER;
				}
				case "set_speed": {
					return MotorCommandType.SET_SPEED;
				}
				case "get_speed": {
					return MotorCommandType.GET_SPEED;
				}
				case "set_position": {
					return MotorCommandType.SET_POSITION;
				}
				case "get_position": {
					return MotorCommandType.GET_POSITION;
				}
				case "get_current": {
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
	 * Auxiliary handler method for commands of type "command"
	 *
	 * @param args the String[] of arguments passed from the user
	 * @return the string containing the output of the command
	 */
	private String handleCommand(String[] args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument was "commands"
		if(args.length == 1) {
			out += listPossibleCommands(HelpType.COMMANDS);
		} else {
			switch(findCommandOperation(args)) {
				case LIST: {
					out += listRegistered(HelpType.COMMANDS);
					break;
				}
				case RUN: {
					commands.get(args[1]).start();
					out += "Ran command " + args[1];
					break;
				}
				case STOP: {
					commands.get(args[1]).cancel();
					out += "Stopped command " + args[1];
					break;
				}
				case INVALID: {
					out += "Invalid argument. Command may not be registered or requested command may not be valid."
							+ "\nTry \"commands:list\" for a list of registered commands or \"commands\" for a list of "
							+ "requestable commands" + "\nArgs: " + Arrays.toString(args);
					break;
				}
			}
		}
		
		System.out.println("returning out");
		return out;
	}
	
	/**
	 * Handles classifying the needed method operation and returning the appropriate enum.
	 *
	 * @param args the String[] of arguments passed from the user 
	 * @return the appropriate CommandCommandType enum
	 */
	private CommandCommandType findCommandOperation(String[] args) {
		if(args[1].equals("list")) {	
			return CommandCommandType.LIST;
		} else if(commands.containsKey(args[1]) && args.length > 2) {
			switch(args[2].toLowerCase()) {
				case "run": {
					return CommandCommandType.RUN;
				}
				case "stop": {
					return CommandCommandType.STOP;
				}
				default: {
					return CommandCommandType.INVALID;
				}
			}
		} else {
			return CommandCommandType.INVALID;
		}
	}
	
	/**
	 * Auxiliary handler method for commands of type "other"
	 *
	 * @param args the String[] of arguments passed from the user
	 * @return the string containing the output of the command
	 */
	private String handleOther(String[] args) {
		String out = "\n";
		
		switch(findOtherOperation(args)) {
			case LIST: {
				out += "Methods:\n" + listRegistered(HelpType.METHODS) + 
						"===============\nMotors:\n" + listRegistered(HelpType.MOTORS) +
						"===============\nCommands:\n" + listRegistered(HelpType.COMMANDS);
				break;
			}
			case HELP: {
				out += listPossibleCommands(HelpType.METHODS) + 
						"\n\n" + listPossibleCommands(HelpType.MOTORS) +
						"\n\n" + listPossibleCommands(HelpType.COMMANDS);
				break;
			}
			case INVALID: {
				out += "Invalid command\n" + listPossibleCommands(HelpType.OTHER);
				break;
			}
		}
		
		return out;
	}
	
	/**
	 * Handles classifying the needed other operation and returns the appropriate enum.
	 *
	 * @param args the String[] of arguments passed from the user 
	 * @return the appropriate OtherCommandType enum
	 */
	private OtherCommandType findOtherOperation(String[] args) {
		switch(args[0]) {
			case "list": {
				return OtherCommandType.LIST;
			}
			case "help": {
				return OtherCommandType.HELP;
			}
			default: {
				return OtherCommandType.INVALID;
			}
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
			case METHODS: {
				out += "Possible commands for type method:\n";
				for(MethodCommandType m : MethodCommandType.values()) {
					if(m.d != "") {
						out += "\n" + m.name() + "-- " + m.d;
					}
				}
				break;
			}
			case MOTORS: {
				out += "Possible commands for type motor:\n";
				for(MotorCommandType m : MotorCommandType.values()) {
					if(m.d != "") {
						out += "\n" + m.name() + "-- " + m.d;
					}
				}
				break;
			}
			case COMMANDS: {
				out += "Possible commands for type command:\n";
				for(CommandCommandType c : CommandCommandType.values()) {
					if(c.d != "") {
						out += "\n" + c.name() + "-- " + c.d;
					}
				}
				break;
			}
			case OTHER: {
				out += "Possible commands:\n";
				for(OtherCommandType o : OtherCommandType.values()) {
					if(o.d != "") {
						out += "\n" + o.name() + "-- " + o.d;
					}
				}
				break;
			}
		}
		
		return out;
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
			case METHODS: {
				for(String key : methods.keySet()) {
					out += key + "\n";
				}
				break;
			}
			case MOTORS: {
				for(String key : motors.keySet()) {
					out += key + "\n";
				}
				break;
			}
			case COMMANDS: {
				for(String key : commands.keySet()) {
					out += key + "\n";
				}
				break;
			}
		}
		
		return out;
	}
	
	/**
	 * Adds the method to the method hashmap.
	 *
	 * @param name the name of the method to be added
	 * @param method the MethodHolder object which wraps the target method
	 */
	public static void addMethod(String name, MethodHolder method) {
		methods.put(name, method);
	}
	
	/**
	 * Adds the motor to the motor hashmap.
	 *
	 * @param name the name of the motor to be added
	 * @param motor the CANTalon246 reference to the target motor
	 */
	public static void addMotor(String name, CANTalon246 motor) {
		motors.put(name, motor);
	}
	
	/**
	 * Adds the command to the command hashmap.
	 *
	 * @param name the name of the command to be added
	 * @param command the instantiated command object
	 */
	public static void addCommand(String name, Command command) {
		commands.put(name, command);
	}
	
}