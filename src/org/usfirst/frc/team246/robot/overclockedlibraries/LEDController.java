package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.util.ArrayList;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

public class LEDController implements Runnable {

	public LEDController() {
		digitalOutputs = new ArrayList<DigitalOutput>();
		ledStates = new ArrayList<LEDState>();
		new Thread(this).start();
	}
	
	public enum LEDState {
		OFF, ON, BLINKING;
	}

	private ArrayList<DigitalOutput> digitalOutputs;
	private ArrayList<LEDState> ledStates;
	

	
	public double startTime = Timer.getFPGATimestamp();	
	
	public int addLED(int portNumber) {
		digitalOutputs.add(new DigitalOutput(portNumber));
		ledStates.add(LEDState.OFF);
		return digitalOutputs.size() - 1;
	}
	
	public void setLEDState(int ledNumber, LEDState state) {
		ledStates.set(ledNumber, state);
	}
	
	public void blink(int ledNumber) {
		if ((Timer.getFPGATimestamp() - startTime) % (RobotMap.LED_BLINK_LENGTH * 2) >= RobotMap.LED_BLINK_LENGTH) {
			digitalOutputs.get(ledNumber).set(true);
		}
		else {
			digitalOutputs.get(ledNumber).set(false);
		}
	}

	
	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < digitalOutputs.size(); i++) {
				LEDState ledState = ledStates.get(i);
				switch (ledState) {
					case OFF: digitalOutputs.get(i).set(false);
						break;
					case ON: digitalOutputs.get(i).set(true);
						break;
					case BLINKING: blink(i);
						break;
				}				
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
