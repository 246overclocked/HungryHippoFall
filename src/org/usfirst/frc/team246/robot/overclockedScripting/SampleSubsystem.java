package org.usfirst.frc.team246.robot.overclockedScripting;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;
import org.usfirst.frc.team246.robot.overclockedScripting.RoboScripting.MethodHolder;

import edu.wpi.first.wpilibj.command.Subsystem;

public class SampleSubsystem extends Subsystem{
	
	CANTalon246 sampleMotor = CANTalon246.init(0, 0);

	public SampleSubsystem() {
		
		//add a sample method to the call reference
		CallReference.addMethod("dummySubsystemMethod", new MethodHolder() {
			
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
		CallReference.addMotor("dummyMotor", sampleMotor);
	}
	
	private void exampleSubsystemMethod(double abc, CANTalon246 motor) {
		System.out.println("received " + abc + motor.getScaledSpeed());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
