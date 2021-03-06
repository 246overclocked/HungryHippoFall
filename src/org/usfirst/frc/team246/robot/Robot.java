
package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.overclockedLibraries.CANTalon246;
import org.usfirst.frc.team246.robot.overclockedLibraries.SampleCommand;
import org.usfirst.frc.team246.robot.overclockedScripting.CallReference;
import org.usfirst.frc.team246.robot.overclockedScripting.RoboScripting;
import org.usfirst.frc.team246.robot.overclockedScripting.RoboScripting.MethodHolder;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;

	
	// Declare subsystem variables here

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	RobotMap.init();
    	
		oi = new OI();

		// Instantiate subsytem objects here
		
		CANTalon246 testMotor1 = RobotMap.testMotor1;
		CANTalon246 testMotor2 = RobotMap.testMotor2;
		CANTalon246 testMotor3 = RobotMap.testMotor3;
		
		testMotor1.changeControlMode(TalonControlMode.PercentVbus);
		testMotor2.changeControlMode(TalonControlMode.PercentVbus);
		testMotor3.changeControlMode(TalonControlMode.PercentVbus);
		
		RoboScripting.addMotor("testMotor1", testMotor1);
		RoboScripting.addMotor("testMotor2", testMotor2);
		RoboScripting.addMotor("testMotor3", testMotor3);
		
		RoboScripting.addMethod("stopAll", new MethodHolder() {
			
			@Override
			public String requiredParams() {
				return "None";
			}
			
			@Override
			public void callMethod(String[] args) throws ArrayIndexOutOfBoundsException, NumberFormatException {
				stopAllMotors();
			}
		});
		
		Command testCommand = new SampleCommand(); 
		
		RoboScripting.addCommand("testCommand", testCommand);
		
		
		
		RoboScripting comms = new RoboScripting();
		comms.initialize(8080);
		
    }
    
    private void stopAllMotors() {
    	CallReference.motors.get("testMotor1").set(0);
    	CallReference.motors.get("testMotor2").set(0);
    	CallReference.motors.get("testMotor3").set(0);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
