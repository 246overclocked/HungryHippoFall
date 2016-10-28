package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.util.ArrayList;

import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage.Severity;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Tools to diagnose sensor readings in SmartDashboard.
 */
public class Diagnostics implements Runnable {

	/** About to brown out. */
	private static boolean nearingVoltageMin = false;

	/** About to pop the main breaker. */
	private static boolean nearingCurrentMax = false;
	
	/** The last registered difference between minimum voltage and actual voltage. */
	private static double voltageLeeway;
	
	/** The last registered time until popping the main breaker. */
	private static double currentSecondsRemaining;

	/**
	 * The time the last {@link UdpAlertService} message was sent about voltage.
	 */
	private static double voltageMessageTime = 0;

	/**
	 * The time the last {@link UdpAlertService} message was sent about current.
	 */
	private static double currentMessageTime = 0;

	/** The starting time for the main breaker timer. */
	private static double breakerStartTime = 0;

	/** The diagnostic output instance. */
	public static Diagnostics instance;

	/** ArrayList of analog inputs. */
	private static ArrayList<DiagnosticsAnalogIn> analogIns = new ArrayList<DiagnosticsAnalogIn>();

	/** ArrayList SRX potentiometers. */
	private static ArrayList<DiagnosticsSRXPotentiometer> SRXPotentiometers = new ArrayList<DiagnosticsSRXPotentiometer>();

	/** ArrayList SRX encoders. */
	private static ArrayList<DiagnosticsSRXEncoder> SRXEncoders = new ArrayList<DiagnosticsSRXEncoder>();
	
	/** ArrayList of SRXs with no sensors. */
	private static ArrayList<DiagnosticsSRXNoSensor> SRXNoSensors = new ArrayList<DiagnosticsSRXNoSensor>();
	
	/** Power Distribution Panel **/
	private static PowerDistributionPanel pdp;
	
	/** Voltage at which the robot will brown out **/
	private static double maxBrownoutVoltage;
	
	/** How close we're willing to let the robot get to brownout voltage **/
	private static double brownoutVoltageTolerance;
	
	/** How soon the breaker will trip based on current **/
	private static double[][] breakerTripTimeVsCurrent;
	
	/** Seconds before main breaker kicks in **/
	private static double breakerTripTimeBuffer;
	

	/**
	 * Initializes the diagnostics instance.
	 */
	public static void initialize(PowerDistributionPanel pdp, double maxBrownoutVoltage, double brownoutVoltageTolerance, 
									double[][] breakerTripTimeVsCurrent, double breakerTripTimeBuffer) {
		if (instance == null) {
			instance = new Diagnostics();
			(new Thread(instance)).start();
		}
		
		Diagnostics.pdp = pdp;
		Diagnostics.maxBrownoutVoltage = maxBrownoutVoltage;
		Diagnostics.brownoutVoltageTolerance = brownoutVoltageTolerance;
		Diagnostics.breakerTripTimeVsCurrent = breakerTripTimeVsCurrent;
		Diagnostics.breakerTripTimeBuffer = breakerTripTimeBuffer;
	
		
	}
	
	/**
	 * Adds an analog input to analogIns, the ArrayList of analog inputs.
	 *
	 * @param analogIn
	 *            the analog input
	 * @param name
	 *            the name of the input
	 */
	public static void addAnalogIn(AnalogIn analogIn, String name) {
		analogIns.add(instance.new DiagnosticsAnalogIn(analogIn, name));
	}

	/**
	 * Adds the SRX with a potentiometer to SRXPotentiometers, the ArrayList of
	 * SRXPotentiometers.
	 *
	 * @param potentiometer
	 *            the SRX potentiometer
	 * @param name
	 *            the name of the potentiometer
	 * @param voltage
	 *            the minimum significant voltage
	 * @param current
	 *            the minimum significant current
	 * @param minSpeed
	 *            the minimum significant speed
	 */
	public static void addSRXPotentiometer(CANTalon246 potentiometer, String name, double voltage, double current, double minSpeed) {
		SRXPotentiometers.add(instance.new DiagnosticsSRXPotentiometer(potentiometer, name, voltage, current, minSpeed));
	}

	/**
	 * Adds the SRX encoder to SRXEncoders, the ArrayList of SRXEncoders.
	 *
	 * @param encoder            the SRX encoder
	 * @param name            the name of the encoder
	 * @param voltage
	 *            the minimum significant voltage
	 * @param current
	 *            the minimum significant current
	 * @param minSpeed
	 *            the minimum significant speed
	 */
	public static void addSRXEncoder(CANTalon246 encoder, String name, double voltage, double current, double minSpeed) {
		SRXEncoders.add(instance.new DiagnosticsSRXEncoder(encoder, name, voltage, current, minSpeed));
	}
	
	/**
	 * Adds the SRX to SRXNoSensors, the ArrayList of SRXNoSensor.
	 *
	 * @param motor
	 *            the SRX motor
	 * @param name
	 *            the name of the motor
	 * @param voltage
	 *            the minimum significant voltage
	 * @param current
	 *            the minimum significant current
	 */
	public static void addSRXNoSensor(CANTalon246 motor, String name, double voltage, double current){
		SRXNoSensors.add(instance.new DiagnosticsSRXNoSensor(motor, name, voltage, current));
	}

	/** The Constant ROBORIO_UNPLUGGED_MIN. */
	static final double ROBORIO_UNPLUGGED_MIN = 0;

	/** The Constant ROBORIO_UNPLUGGED_MAX. */
	static final double ROBORIO_UNPLUGGED_MAX = 0;

	/** The Constant MIN_POT_VALUE_CHANGE_PER_SECOND. */
	static final double MIN_POT_VALUE_CHANGE_PER_SECOND = .2;

	/** The Constant MIN_ENCODER_VALUE_CHANGE_PER_SECOND. */
	static final double MIN_ENCODER_VALUE_CHANGE_PER_SECOND = 25;

	/** The Constant MIN_MOTOR_VALUE. */
	static final double MIN_MOTOR_VALUE = .2;

	/**
	 * Nearing voltage min.
	 *
	 * @return true, if about to brown out
	 */
	public static boolean nearingVoltageMin() {
		return nearingVoltageMin;
	}

	/**
	 * Nearing current max.
	 *
	 * @return true, if about to trip the main breaker
	 */
	public static boolean nearingCurrentMax() {
		return nearingCurrentMax;
	}
	
	public static double getTimeUntilBreakerTrips() {
		return currentSecondsRemaining;
	}
	
	public static double getVoltageLeeway() {
		return voltageLeeway;
	}

	/**
	 * Send a {@link UdpAlertService} alert about the voltage if
	 * nearingVoltageMin
	 */
	private static void sendVoltageAlert() {
		if (nearingVoltageMin && Timer.getFPGATimestamp() - voltageMessageTime >= 1) {
			String voltageAlert = String.format("Nearing the Voltage minimum. Voltage: %.2f", pdp.getVoltage());
			UdpAlertService.sendAlert(new AlertMessage(voltageAlert).severity(Severity.WARNING));
			voltageMessageTime = Timer.getFPGATimestamp();
		}
	}

	/**
	 * Send a {@link UdpAlertService} alert about the current if
	 * nearingCurrentMax
	 *
	 * @param secondsRemaining
	 *            the seconds remaining before tripping the main breaker
	 */
	private static void sendCurrentAlert(double secondsRemaining) {
		if (nearingCurrentMax && Timer.getFPGATimestamp() - currentMessageTime >= 1) {
			String currentAlert = String.format(
					"Nearing the Current maximum. Current: %.2f Seconds Remaining: %f",
					pdp.getTotalCurrent(), secondsRemaining);
			UdpAlertService.sendAlert(new AlertMessage(currentAlert).severity(Severity.WARNING));
			currentMessageTime = Timer.getFPGATimestamp();
		}
	}

	/**
	 * Monitor voltage by setting nearingVoltageMin to true if and only if the
	 * voltage is less than RobotMap.BROWNOUT_VOLTAGE_TOLERANCE above the
	 * RobotMap.MAX_BROWNOUT_VOLTAGE.
	 */
	private static void monitorVoltage() {
		nearingVoltageMin = pdp.getVoltage() - maxBrownoutVoltage < brownoutVoltageTolerance;
		sendVoltageAlert();
		voltageLeeway = brownoutVoltageTolerance - (pdp.getVoltage() - maxBrownoutVoltage);
	}

	/**
	 * Monitor current by setting nearingCurrentMax to true if and only if in
	 * RobotMap.BREAKER_TRIP_TIME_BUFFER or less the main breaker will break.
	 * Makes this calculation using {@link DataInterpolator} and
	 * RobotMap.BREAKER_TRIP_TIME_VS_CURRENT.
	 */
	private static void monitorCurrent() {
		if (pdp.getTotalCurrent() > 120) {
			if (breakerStartTime == 0) {
				breakerStartTime = Timer.getFPGATimestamp();
			} else {
				double secondsInHighCurrent = Timer.getFPGATimestamp() - breakerStartTime;
				double secondsRemaining = DataInterpolator.interpolateValue(secondsInHighCurrent,
						breakerTripTimeVsCurrent);

				nearingCurrentMax = secondsRemaining <= breakerTripTimeBuffer;
				currentSecondsRemaining = secondsRemaining;
				sendCurrentAlert(secondsRemaining);
			}
		} else {
			breakerStartTime = 0;
			nearingCurrentMax = false;
		}
	}

	/**
	 * Monitor electricity. Calls both monitorVoltage() and monitorCurrent().
	 */
	private static void monitorElectricity() {
		monitorVoltage();
		monitorCurrent();
	}

	/**
	 * Run updates the smart dashboard numbers and checks if any of the motors
	 * are unplugged or backwards, or if the sensors are unplugged
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			// current and voltage monitoring
//			monitorElectricity();

			double[][] previousAnalogInValues = new double[analogIns.size()][150];
			for (int i = 0; i < analogIns.size(); i++) {
				DiagnosticsAnalogIn ai = analogIns.get(i);
				SmartDashboard.putNumber(ai.name, ai.sensor.get());
				for (int c = previousAnalogInValues.length - 1; c > 0; c--) {
					previousAnalogInValues[i][c] = previousAnalogInValues[i][c - 1];
				}
				previousAnalogInValues[i][0] = ai.sensor.get();
				if (ai.sensor.onRIO) {

				}
			}
			for (int i = 0; i < SRXEncoders.size(); i++) {
				DiagnosticsSRXEncoder e = SRXEncoders.get(i);
				SmartDashboard.putNumber(e.name, e.talon.getScaledSpeed());
				SmartDashboard.putNumber(e.name+"Current", e.talon.getOutputCurrent());
//				if (e.talon.getOutputVoltage()> e.voltage){
//					if (e.talon.getOutputCurrent() < e.current){
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " motor is unplugged!").severity(Severity.ERROR));
//					} else if (e.talon.getSpeed() < e.minSpeed){
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " encoder is unplugged!").severity(Severity.ERROR));
//					} else if (e.talon.getSpeed() * e.talon.getOutputVoltage() < e.minSpeed * e.voltage * -1){//Checks that the output voltage and sensor velocity have the same sign
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " is plugged in backwards").severity(Severity.ERROR));
//					}
//				}
			}
			for (int i = 0; i < SRXPotentiometers.size(); i++) {
				DiagnosticsSRXPotentiometer e = SRXPotentiometers.get(i);
				SmartDashboard.putNumber(e.name, e.talon.getScaledPosition());
				SmartDashboard.putNumber(e.name+"Current", e.talon.getOutputCurrent());
//				if (e.talon.getOutputVoltage()> e.voltage){
//					if (e.talon.getOutputCurrent() < e.current){
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " motor is unplugged!").severity(Severity.ERROR));
//					} else if (e.talon.getSpeed() < e.minSpeed){
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " pot is unplugged!").severity(Severity.ERROR));
//					} else if (e.talon.getSpeed() * e.talon.getOutputVoltage() < e.minSpeed * e.voltage * -1){//Checks that the output voltage and sensor velocity have the same sign
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " is plugged in backwards").severity(Severity.ERROR));
//					}
//				}
			}
			for (int i = 0; i < SRXNoSensors.size(); i++){
				DiagnosticsSRXNoSensor e = SRXNoSensors.get(i);
				SmartDashboard.putNumber(e.name, e.talon.getPosition());
				SmartDashboard.putNumber(e.name+"Current", e.talon.getOutputCurrent());
//				if (e.talon.getOutputVoltage()> e.voltage){
//					if (e.talon.getOutputCurrent() < e.current){
//						UdpAlertService.sendAlert(new AlertMessage(e.name + " motor is unplugged!").severity(Severity.ERROR));
//					}
//				}
			}
			Timer.delay(.1);
		}
	}

	/**
	 * The Class DiagnosticsAnalogIn.
	 */
	private class DiagnosticsAnalogIn {

		/** The analog sensor. */
		public AnalogIn sensor;

		/** The name of the sensor. */
		public String name;

		/**
		 * Instantiates a new analog sensor.
		 *
		 * @param sensor
		 *            the sensor object
		 * @param name
		 *            the sensor name
		 */
		public DiagnosticsAnalogIn(AnalogIn sensor, String name) {
			this.sensor = sensor;
			this.name = name;
		}
	}


	/**
	 * The Class DiagnosticsSRXPotentiometer.
	 */
	private class DiagnosticsSRXPotentiometer {

		/** The CANtalon246 object . */
		public CANTalon246 talon;

		/** The name of the SRXPotentiometer. */
		public String name;
		
		/** The minimum significant voltage from this SRX. */
		public double voltage;

		/** The minimum significant current from this SRX. */
		public double current;
		
		/** The minimum significant speed for this motor. */
		public double minSpeed;
		
		/**
		 * Instantiates a new diagnostics SRX potentiometer.
		 *
		 * @param talon
		 *            the CANTalon potentiometer
		 * @param name
		 *            the name of the potentiometer
		 * @param voltage
		 *            The minimum significant voltage from this SRX
		 * @param current
		 *            The minimum significant current from this SRX
		 * @param minSpeed
		 *            The minimum significant speed for this motor
		 */
		public DiagnosticsSRXPotentiometer(CANTalon246 talon, String name, double voltage, double current, double minSpeed) {
			this.talon = talon;
			this.name = name;
			this.voltage = voltage;
			this.current = current;
			this.minSpeed = minSpeed;
		}
	}

	/**
	 * The Class DiagnosticsSRXEncoder.
	 */
	private class DiagnosticsSRXEncoder {

		/** The CANTalon246. */
		public CANTalon246 talon;

		/** The name of the encoder. */
		public String name;
		
		/** The minimum significant voltage from this SRX. */
		public double voltage;

		/** The minimum significant current from this SRX. */
		public double current;
		
		/** The minimum significant speed for this motor. */
		public double minSpeed;

		/**
		 * Instantiates a new diagnostics SRX encoder.
		 *
		 * @param talon
		 *            the CANTalon246
		 * @param name
		 *            the name of the encoder
		 * @param voltage
		 *            The minimum significant voltage from this SRX
		 * @param current
		 *            The minimum significant current from this SRX
		 * @param minSpeed
		 *            The minimum significant speed for this motor
		 */
		public DiagnosticsSRXEncoder(CANTalon246 talon, String name, double voltage, double current, double minSpeed) {
			this.talon = talon;
			this.name = name;
			this.voltage = voltage;
			this.current = current;
			this.minSpeed = minSpeed;
			
		}
	}
	
	/**
	 * The diagnostics class for SRXs with no sensors attached.
	 */
	private class DiagnosticsSRXNoSensor {
		
		/** The CANTalon246. */
		public CANTalon246 talon;
		
		/** The name of the motor. */
		public String name;
		
		/** The minimum significant voltage from this SRX. */
		public double voltage;

		/** The minimum significant current from this SRX. */
		public double current;
		
		/**
		 * Instantiates a new diagnostics srx with no sensors.
		 *
		 * @param talon
		 *            the CANTalon246
		 * @param name
		 *            the name of the motor
		 * @param voltage
		 *            The minimum significant voltage from this SRX
		 * @param current
		 *            The minimum significant current from this SRX
		 */
		public DiagnosticsSRXNoSensor(CANTalon246 talon, String name, double voltage, double current){
			this.talon = talon;
			this.name = name;
			this.voltage = voltage;
			this.current = current;
		}
	}
}
