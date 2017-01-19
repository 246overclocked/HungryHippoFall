package org.usfirst.frc.team246.robot.overclockedScripting;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

import edu.wpi.first.wpilibj.command.Command;

public class RoboScripting implements Runnable {
	
	private static RoboScripting instance; // The instance of this singleton
	private static int port; // The TCP port to listen to
	private static ServerSocket socket; // The TCP socket to listen to
	private static boolean isConnected = false; // If a connection with the client has been established
	
	private CallReference reference = new CallReference();
	
	/**
	 * Initialize this singleton. This method will first open a new socket to
	 * listen to, then start running a second thread which responds to commands.
	 * 
	 * @param portNumber
	 *            The port number for communication with the client
	 */
	public static void initialize(int portNumber) {
		port = portNumber;
		
		isConnected = connect();
		
		instance = new RoboScripting();
		(new Thread(instance)).start();
	}
	
	
	/**
	 * Instantiates the socket to a new ServerSocket listening to {@link #port}.
	 * 
	 * @return If the socket was created successfully.
	 */
	private static boolean connect() {
		try {
			socket = new ServerSocket(port);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void run() {
		while(true) {
			
			if(!isConnected) {
				isConnected = connect();
				continue;
			}
			
			try (Socket clientSocket = socket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true)) {
				
				//keep receiving data until client terminates connection
				while(true) {
					String msg = inFromClient.readLine();
					System.out.println("Msg from client was: " + msg);
					 
					//parse, handle, then send back resulting data
					String[] args = parse(msg);
					
					//user sent KeyboardInterrupt (Cntrl-C) or "exit"
					//close and reopen socket
					if(args == null) {
						try {
							socket.close();
						} catch (IOException e) {
							System.out.println("Error closing socket");
							e.printStackTrace();
						}
						
						isConnected = false;
						break;
					}
					
					String out = call(parse(msg));
					//send data and flush
					outToClient.println(out);
					outToClient.flush();
					System.out.println("sent:" + out);
					
				}
			
			} catch (IOException e) {}	
		}
	}
		
	public String[] parse(String msg) {
		if(msg == null) {
			return null;
		} else if(msg.equals("exit")) {
			return null;
		} else {
			String[] args = new String[msg.split(":").length];
			//remove whitespace
			args = msg.replaceAll("\\s+", "").split(":");
			System.out.println("Arguments: " + Arrays.toString(args));
			
			return args;
		}
	}
	
	public String call(String[] args) {
		return reference.handle(args);
	}
	
	
	//ACCESS METHODS
	
	/**
	 * Adds the method to the CallReference method hashmap.
	 *
	 * @param name the name of the method to be added
	 * @param method the MethodHolder object which wraps the target method
	 */
	public static void addMethod(String name, MethodHolder method) {
		CallReference.addMethod(name, method);
	}
	
	/**
	 * Adds the motor to the CallReference motor hashmap.
	 *
	 * @param name the name of the motor to be added
	 * @param motor the CANTalon246 reference to the target motor
	 */
	public static void addMotor(String name, CANTalon246 motor) {
		CallReference.addMotor(name, motor);
	}
	
	/**
	 * Adds the command to the CallReference command hashmap.
	 *
	 * @param name the name of the command to be added
	 * @param command the instantiated command object
	 */
	public static void addCommand(String name, Command command) {
		CallReference.addCommand(name, command);
	}
	
	/**
	 * An anonymous in-line class used to wrap methods in order to store them in the CallReference.
	 */
	public static abstract class MethodHolder {
		
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
		 * in Java, ex. "double, CANTalon246, String". If no parameters are required,
		 * return "None".
		 *
		 * @return the string with the required parameters for the wrapped methods.
		 */
		public abstract String requiredParams();
	}
	
}
