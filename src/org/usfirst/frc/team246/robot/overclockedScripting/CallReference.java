package org.usfirst.frc.team246.robot.overclockedScripting;

import java.util.Arrays;
import java.util.HashMap;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

public class CallReference {

	//contains all registered methods
	private static HashMap<String, MethodHolder> methods = new HashMap<>();
	
	//contains all registered motors
	private static HashMap<String, CANTalon246> motors = new HashMap<>();	
	
	//denotes the category of help which should be returned
	private enum HelpType {
		ALL, METHODS, MOTORS, OTHER
	}
	
	//possible commands for the type all
	private final String[] ALL_DESCRIPTIONS = {
		"list-- lists all registered methods and motors"
	};
	
	//possible commands for the type method
	private final String[] METHOD_DESCRIPTIONS = {
		"list-- lists all registered methods",
		"{METHOD_NAME}-- executes METHOD_NAME if it is registered"
	};
	
	//possible commands for the type motor
	private final String[] MOTOR_DESCRIPTIONS = {
			"list-- lists all registered motors",
			"{MOTOR_NAME:SPEED}-- runs MOTOR_NAME at SPEED if it is registered",
			"{MOTOR_NAME:GET}-- gets the speed of MOTOR_NAME if it is registered"
	};
	
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
	
	
	private String handleMethod(String... args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument is "methods"
		if(args.length == 1)  {
		
			out += listPossibleCommands(HelpType.METHODS);
		
		//arguments are "methods" and "list"
		} else if(args[1].equals("list")) {
		
			out += listRegistered(HelpType.METHODS);
			
		} else {
		
			//if the method is registered
			if(methods.containsKey(args[1])) {
				
				//call method and return confirmation
				methods.get(args[1]).callMethod();
				out += "Method successfully called";
			
			//method wasn't found 
			//(also where an unlisted command such as method:aiowdja will go)
			} else {
				out+= "Method not found";
			}
		
		}
		
		return out;
	}
	
	
	private String handleMotor(String[] args) {
		
		//the string to be returned
		String out = "\n";
		
		//only argument was "motors"
		if(args.length == 1) {
		
			out += listPossibleCommands(HelpType.MOTORS);
		
		//arguments were "motors" and "list"
		} else if(args[1].equals("list")) {
		
			out += listRegistered(HelpType.MOTORS);
			
		} else {
		
			//if motor is registered and there is a second parameter
			if(motors.containsKey(args[1]) && args.length > 2) {	
				
				//if the second argument is a valid integer, set motor at speed
				try {
					motors.get(args[1]).scaledSet(Double.parseDouble(args[2]));
					out += "Set " + args[1] + " at " + Integer.parseInt(args[2]);
					
				//if not a valid integer, check for other arguments
				} catch (NumberFormatException e) {
					
					//second argument is "get", return the speed of the specified motor
					if(args[2].equals("get")) {
						out += args[1] + " is at speed: " + motors.get(args[1]).getScaledSpeed();
						
					//not a listed second argument
					} else {
						out += "Invalid second argument for type motor, try \"motors\" for help";
					}
				}
				
			//motor is not registered
			} else if(!motors.containsKey(args[1])) {
				out += "Motor not found";
				
			//specified command for type motor was not found
			} else {
				out += "Invalid second argument";
			}
		}
		
		return out;
	}
	
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
				
				for(String d : METHOD_DESCRIPTIONS) {
					out += d + "\n";
				}
				
				return out;
			}
			case MOTORS: {
				out += "Possible commands for type motor:\n";
				
				for(String d : MOTOR_DESCRIPTIONS) {
					out += d + "\n";
				}
				
				return out;
			}
			default: {
				return "";
			}
		}
	}
	
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
	
	public static void addMethod(String name, MethodHolder method) {
		methods.put(name, method);
	}
	
	public static void addMotor(String name, CANTalon246 motor) {
		motors.put(name, motor);
	}
	
}
