package org.usfirst.frc.team246.robot.overclockedLibraries;

import com.ctre.CANTalon;

/** 246 wrapper for the CANTalon class */
public class CANTalon246 extends CANTalon {

	private double offset;// offset for pots and magnetic encoders
	private double range;// range for pots
	private double scale;// scale for encoder
	private double positionTolerance;// tolerance for onTarget() when in position mode
	private double speedTolerance; // tolerance for onTarget() when in speed mode
	
	private boolean isSensorReversed = false;

	/**
	 * Type of sensor the CANTalon is connected to
	 * 
	 * @author Owen Gillespie
	 *
	 */
	public enum SensorType {
		// this class can create the following types of CANTalons
		POTENTIOMETER(), ENCODER(), MAGNETIC_ENCODER(), OTHER()
	}

	/**
	 * The units of time used for setting and getting the speed, when the
	 * CANTalon is in Speed mode. Used in {@link #scaledSet(double) scaledSet()}
	 * , {@link #getScaledSetpoint() getScaledSetpoint()}, and
	 * {@link #getScaledSpeed() getScaledSpeed()} when in Speed mode.
	 *
	 * Set unit using {@link CANTalon246#setSpeedUnit(SpeedUnit)} and get unit
	 * using {@link CANTalon246#getSpeedUnit()}. Default SpeedUnit is PerSecond.
	 */
	public enum SpeedUnit {
		PerSecond, PerMinute
	}

	private SensorType type;
	private SpeedUnit speedUnit = SpeedUnit.PerSecond; // per sec by default

	/**
	 * Creates a new CANTalon246 object
	 * 
	 * @param deviceNumber
	 *            CAN device number for this Talon
	 * @param controlPeriodMs
	 *            Internal PID loop period
	 */
	private CANTalon246(int deviceNumber, int controlPeriodMs) {
		super(deviceNumber, controlPeriodMs);
	}

	/**
	 * Creates a {@link CANTalon246} connected to a potentiometer
	 * 
	 * @param deviceNumber
	 *            CAN device number for this {@link CANTalon}
	 * @param controlPeriodMs
	 *            Internal PID loop period
	 * @param offset
	 *            Non-scaled units to account for potentiometer offset,
	 *            subtracted before scaling in {@link #scaledSet(double)
	 *            scaledSet()}
	 * @param range
	 *            Range of possible values for the potentiometer
	 * @return A new {@link CANTalon246} object
	 */
	public static CANTalon246 initWithPot(int deviceNumber, int controlPeriodMs, double offset, double range) {
		CANTalon246 potTalon = new CANTalon246(deviceNumber, controlPeriodMs);
		potTalon.type = SensorType.POTENTIOMETER;
		potTalon.offset = offset;
		potTalon.range = range;
		return potTalon;
	}

	/**
	 * Creates a {@link CANTalon246} connected to an encoder
	 * 
	 * @param deviceNumber
	 *            CAN device number for this {@link CANTalon}
	 * @param controlPeriodMs
	 *            Internal PID loop period
	 * @param scale
	 *            Distance per tick
	 * @return A new {@link CANTalon246} object
	 */
	public static CANTalon246 initWithEncoder(int deviceNumber, int controlPeriodMs, double scale) {
		CANTalon246 encoderTalon = new CANTalon246(deviceNumber, controlPeriodMs);
		encoderTalon.type = SensorType.ENCODER;
		encoderTalon.scale = scale;
		return encoderTalon;
	}

	/**
	 * Creates a {@link CANTalon246} connected to a magnetic encoder.
	 *
	 * @param deviceNumber
	 *            CAN device number for this {@link CANTalon}
	 * @param controlPeriodMs
	 *            Internal PID loop period
	 * @param scale
	 *            Distance per tick
	 * @param offset
	 *            Non-scaled units to account for absolute magnetic encoder
	 *            offset. Used in {@link #getScaledMagneticEncAbsolutePosition()
	 *            getScaledMagneticEncAbsolutePosition()}
	 * @return A new {@link CANTalon246} object
	 */
	public static CANTalon246 initWithMagneticEncoder(int deviceNumber, int controlPeriodMs, double scale, double offset) {
		CANTalon246 magEncTalon = new CANTalon246(deviceNumber, controlPeriodMs);
		magEncTalon.type = SensorType.MAGNETIC_ENCODER;
		magEncTalon.scale = scale;
		magEncTalon.offset = offset;
		return magEncTalon;
	}

	/**
	 * Creates a {@link CANTalon246} connected to an unknown or no sensor
	 * 
	 * @param deviceNumber
	 *            CAN device number for this {@link CANTalon}
	 * @param controlPeriodMs
	 *            Internal PID loop period
	 * @return A new {@link CANTalon} object
	 */
	public static CANTalon246 init(int deviceNumber, int controlPeriodMs) {
		CANTalon246 otherTalon = new CANTalon246(deviceNumber, controlPeriodMs);
		otherTalon.type = SensorType.OTHER;
		return otherTalon;
	}
	
	public SensorType getSensorType() {
		return type;
	}

	/**
	 * Convert speed per second to per unit based on the set {@link SpeedUnit}.
	 * Default is unit/sec.
	 *
	 * @param speedPerSecond
	 *            the speed in units per second
	 * @return the converted speed in units specified by
	 *         {@link #setSpeedUnit(SpeedUnit) setSpeedUnit()}
	 */
	private double speedPerSecondToPerUnit(double speedPerSecond) {
		if (this.speedUnit == SpeedUnit.PerSecond) {
			return speedPerSecond;
		} else if (this.speedUnit == SpeedUnit.PerMinute) {
			return UnitConverter.PerSecondToPerMinute(speedPerSecond);
		}
		return speedPerSecond;
	}

	/**
	 * Convert speed per unit to per second based on the set {@link SpeedUnit}.
	 * Unless unit is set using {@link #setSpeedUnit(SpeedUnit) setSpeedUnit()},
	 * assumes given speed is in units/sec.
	 *
	 * @param speedPerUnit
	 *            the speed in units specified by
	 *            {@link #setSpeedUnit(SpeedUnit) setSpeedUnit()}
	 * @return the speed converted into units/sec
	 */
	private double speedPerUnitToPerSecond(double speedPerUnit) {
		if (this.speedUnit == SpeedUnit.PerSecond) {
			return speedPerUnit;
		} else if (this.speedUnit == SpeedUnit.PerMinute) {
			return UnitConverter.PerMinuteToPerSecond(speedPerUnit);
		}
		return speedPerUnit;
	}

	/**
	 * Gets the scaled setpoint. Will automatically account for position or
	 * speed mode and conditionally apply the offset accordingly.
	 *
	 * If in speed mode, default units are units/second, unless otherwise set
	 * by {@link #setSpeedUnit(SpeedUnit) setSpeedUnit()}
	 *
	 * @return the scaled setpoint
	 */
	public double getScaledSetpoint() {
		// N.B. The documentation says that we should be converting to
		// ticks/10ms, but in practice we have found that the PID setpoints are
		// set correctly when we convert to ticks/100ms
		if (type == SensorType.ENCODER || type == SensorType.MAGNETIC_ENCODER) {
			if (this.getControlMode() == TalonControlMode.Speed) {
				return speedPerSecondToPerUnit(UnitConverter.Per100msToPerSecond(getSetpoint() * scale));
			} else if (this.getControlMode() == TalonControlMode.Position) {
				return UnitConverter.Per100msToPerSecond(getSetpoint() * scale);
			}
		} else if (type == SensorType.POTENTIOMETER) {
			if (this.getControlMode() == TalonControlMode.Speed) {
				return speedPerSecondToPerUnit(UnitConverter.Per100msToPerSecond(getSetpoint() / 1024. * range));
			} else if (this.getControlMode() == TalonControlMode.Position) {
				return UnitConverter.Per100msToPerSecond(getSetpoint() / 1024. * range - offset);
			}
		}
		return UnitConverter.Per100msToPerSecond(getSetpoint());
	}

	/**
	 * Returns the sensor position with scaling
	 * 
	 * @return Scaled sensor position
	 */
	public double getScaledPosition() {
		if (type == SensorType.POTENTIOMETER) {
			return getPosition() / 1024. * range - offset;
		} else if (type == SensorType.ENCODER || type == SensorType.MAGNETIC_ENCODER) {
			return getPosition() * scale;
		} else {
			return getPosition();
		}
	}

	/**
	 * Sets the position with scaling.
	 *
	 * @param position
	 *            the new scaled position
	 */
	public void setScaledPosition(double position) {
		if(type == SensorType.ENCODER || type == SensorType.MAGNETIC_ENCODER){
			setPosition(position/scale);
		} else if(type == SensorType.POTENTIOMETER){
			setPosition((position + offset)*1024/range);
		}
	}

	/**
	 * Returns the scaled sensor speed
	 * 
	 * @return Scaled sensor speed
	 */
	public double getScaledSpeed() {
		if (type == SensorType.ENCODER || type == SensorType.MAGNETIC_ENCODER) {
			return speedPerSecondToPerUnit(UnitConverter.Per100msToPerSecond(getSpeed()) * scale);
		} else if (type == SensorType.POTENTIOMETER){
			return speedPerSecondToPerUnit(UnitConverter.Per100msToPerSecond(getSpeed()) / 1024 * range);
		} else {
			throw new RuntimeException("No sensor declared");
		}
	}
	
	/**
	 * Sets the PID Setpoint scaled based on the sensor scaling constants.
	 *
	 * If in speed mode, the input is interpreted as units/sec by default.
	 * However, other other units can be used if set using
	 * {@link #setSpeedUnit(SpeedUnit) setSpeedUnit()} method.
	 *
	 * @param outputValue
	 *            the output value
	 */
	public void scaledSet(double outputValue) {
		// N.B. The documentation says that we should be converting to
		// ticks/10ms, but in practice we have found that the PID setpoints are
		// set correctly when we convert to ticks/100ms
		if(type == SensorType.ENCODER || type == SensorType.MAGNETIC_ENCODER){
			if (getControlMode() == TalonControlMode.Speed){
				if (type == SensorType.MAGNETIC_ENCODER) {
					System.out.println(String.format("Passed to set(%.3f)", UnitConverter.PerSecondToPer100ms(speedPerUnitToPerSecond(outputValue/scale))));
				}
				set(UnitConverter.PerSecondToPer100ms(speedPerUnitToPerSecond(outputValue/scale)));
			} else if (getControlMode() == TalonControlMode.Position){
				set(outputValue/scale);
			}

		} else if(type == SensorType.POTENTIOMETER){
			if (getControlMode() == TalonControlMode.Speed){
				set(UnitConverter.PerSecondToPer100ms(speedPerUnitToPerSecond(outputValue*1024/range)));
			} else if(getControlMode() == TalonControlMode.Position){
				set((outputValue + offset)*1024/range);
			} else {
				throw new RuntimeException("Invalid Talon Control Mode");
			}
		}
	}
	
	
	
	/**
	 * Zeroes the encoder at newPosition.
	 *
	 * @param newPosition the new 0 position
	 */
	public void setScaledEncPosition(double newPosition){
		setEncPosition((int)(newPosition / scale));
	}
	
	@Override
	public void reverseSensor(boolean flipped) {
		isSensorReversed = flipped;
		super.reverseSensor(flipped);
	}
	
	/**
	 * Returns if the sensor has been reversed
	 * 
	 * @return true if the sensor has been reversed
	 */
	public boolean isSensorReverse() {
		return isSensorReversed;
	}

	/**
	 * Gets the scaled absolute position for a magnetic encoder.
	 *
	 * @return the scaled absolute position
	 */
	public double getScaledMagneticEncAbsolutePosition() {
		return (isSensorReversed ? -1.:1.) * this.getPulseWidthPosition() / 4092. * scale - offset;
	}

	/**
	 * Gets the offset of potentiometer
	 * 
	 * @return Potentiometer offset
	 */
	public double getOffset() {
		return this.offset;
	}

	/**
	 * Sets the potentiometer offset
	 * 
	 * @param offset
	 *            Units subtracted after potentiometer position is scaled
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
	 * Gets the range of a potentiometer
	 * 
	 * @return Potentiometer range
	 */
	public double getRange() {
		return this.range;
	}

	/**
	 * Sets the potentiometer range
	 * 
	 * @param range
	 *            Range of possible values for the potentiometer
	 */
	public void setRange(double range) {
		this.range = range;
	}

	/**
	 * Gets the scale of an encoder
	 * 
	 * @return Encoder scale
	 */
	public double getScale() {
		return this.scale;
	}

	/**
	 * Sets the encoder scale
	 * 
	 * @param scale
	 *            Distance per tick
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	/**
	 * Gets the speed units used in {@link #scaledSet(double) scaledSet()},
	 * {@link #getScaledSetpoint() getScaledSetpoint()}, and
	 * {@link #getScaledSpeed() getScaledSpeed()}
	 *
	 * @return the speed unit
	 */
	public SpeedUnit getSpeedUnit() {
		return this.speedUnit;
	}

	/**
	 * Sets the speed units. Sets how the input to {@link #scaledSet(double)
	 * scaleSet()} is interpreted.
	 *
	 * @param unit
	 *            the new speed units
	 */
	public void setSpeedUnit(SpeedUnit unit) {
		this.speedUnit = unit;
	}

	/**
	 * Gets the PID tolerance
	 * 
	 * @return The PID tolerance
	 */
	public double getAbsolutePositionTolerance() {
		return positionTolerance;
	}
	
	/**
	 * Sets the absolute value for the PID tolerance
	 * 
	 * @param tolerance
	 *            The value to set the tolerance to
	 */
	public void setAbsoluteSpeedTolerance(double tolerance) { //TODO figure out units
		this.speedTolerance = tolerance;
	}
	
	/**
	 * Gets the PID tolerance
	 * 
	 * @return The PID tolerance
	 */
	public double getAbsoluteSpeedTolerance() {
		return speedTolerance;
	}
	
	/**
	 * Sets the absolute value for the PID tolerance
	 * 
	 * @param tolerance
	 *            The value to set the tolerance to
	 */
	public void setAbsolutePositionTolerance(double tolerance) { //TODO figure out units
		this.positionTolerance = tolerance;
	}
		
	/**
	 * Returns whether the PID is within the allowed tolerance of the setpoint
	 * 
	 * @return
	 */
	public boolean onTarget() {
		
		if (getControlMode() == TalonControlMode.Position && Math.abs(getScaledPosition() - getScaledSetpoint()) < positionTolerance
				|| getControlMode() == TalonControlMode.Speed && Math.abs(getScaledSpeed() - getScaledSetpoint()) < speedTolerance) {
			return true;
		}
		else {
			return false;
		}
	}
}
