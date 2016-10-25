package org.usfirst.frc.team246.robot.overclockedScripting;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;

public class SampleSubsystem {
	
	CANTalon246 sampleMotor = CANTalon246.init(0, 0);

	public SampleSubsystem() {
		
		//add a sample method to the call reference
		CallReference.addMethod("sampleSubsystemMethod", new MethodHolder() {
			
			@Override
			public void callMethod(String[] params) throws ArrayIndexOutOfBoundsException, 
															NumberFormatException {
				exampleSubsystemMethod(Double.parseDouble(params[0]), CallReference.motors.get(params[1]));
			}
			
			@Override
			public String requiredParams() {
				return "double, CANTalon246";
			}
		});
		
		//add a sample motor to the call reference
		CallReference.addMotor("sampleMotor", sampleMotor);
	}
	
	private void exampleSubsystemMethod(double abc, CANTalon246 motor) {
		System.out.println("received " + abc + motor.getScaledSpeed());
	}
}
