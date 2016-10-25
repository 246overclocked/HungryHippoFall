package org.usfirst.frc.team246.robot.overclockedScripting;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class PythonComms implements Runnable {
	
	private static PythonComms instance; // The instance of this singleton
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
		
		instance = new PythonComms();
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
					outToClient.println(call(parse(msg)));
				}
			
			} catch (IOException e) {}
			
		}
	}
	
	public String[] parse(String msg) {
		
		String[] args = new String[msg.split(":").length];
		args = msg.replaceAll("\\s+", "").split(":");
		System.out.println("Arguments: " + Arrays.toString(args));
		
		return args;
	}
	
	public String call(String[] args) {
		return reference.handle(args);
	}
}
