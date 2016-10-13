package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.util.LinkedList;

import org.usfirst.frc.team246.robot.RobotMap.NeopixelBytecodes;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;

public class NeopixelController extends I2C implements Runnable {
	
	private NeopixelBytecodes lastCommand;
	private LinkedList<byte[]> commandQueue;
	private boolean arduinoAlive = true;

	public NeopixelController(Port port, int deviceAddress) {
		super(port, deviceAddress);
		commandQueue = new LinkedList<byte[]>();
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while(true) {
			// continuously poll the queue and send commands FIFO style
			byte[] polledBytecode = commandQueue.peek();

			if (polledBytecode != null) {
				arduinoAlive = writeWithConfirmation(polledBytecode);
				if (arduinoAlive) {
					System.out.println("Message sent: " + commandQueue.peek());
					commandQueue.removeFirst();
				}
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send a bytecode command over serial to the arduino.
	 * 
	 * If the last sent command was the exact same as the given
	 * neopixelBytecode, no command will be sent.
	 *
	 * @param neopixelBytecode
	 *            the neopixel bytecode
	 */
	public void sendCommand(NeopixelBytecodes neopixelBytecode) {
		if (lastCommand != neopixelBytecode) {
			lastCommand = neopixelBytecode;
			byte[] bytecode = neopixelBytecode.getBytecode();
			commandQueue.addLast(bytecode);
			System.out.printf("ADDED COMMAND TO QUEUE: %s    QUEUE HEAD: %d\n", neopixelBytecode.name(), commandQueue.peek()[0]);
		}
	}

	/**
	 * Write to neopixel serial port with confirmation that arduino is alive.
	 *
	 * @param bytecode
	 *            the bytecode to send
	 * @return true, if arduino responds that it received the message; false otherwise.
	 */
	private boolean writeWithConfirmation(byte[] bytecode) {
		byte[] receiveBytes = new byte[1];
		transaction(bytecode, bytecode.length, receiveBytes, receiveBytes.length);
		return true; //TODO: Get actual confirmation that the message was received
	}

}
