package org.usfirst.frc.team246.robot.overclockedLibraries;

public class PIDConstants {

	public double kP;
	public double kI;
	public double kD;
	public double kF;
	public double period;
	public double izone;
	public double closedLoopRamp;
	public int profile;

	/**
	 * Creates a new {@link PIDConstants} object.
	 *
	 * @param kP
	 *            The Proportional constant for a PIDController.
	 * @param kI
	 *            The Integral constant for a PIDController.
	 * @param kD
	 *            The Differential constant for a PIDController.
	 * @param kF
	 *            The Feedforward constant for a PIDController.
	 * @param period
	 *            The loop time for a PIDController (in milliseconds).
	 * @param izone
	 *            The width of the integration zone.
	 * @param closedLoopRamp
	 *            Limits the rate at which the throttle will change. Only
	 *            affects position and speed closed loop modes. Max 85.
	 * @param profile
	 *            The profile number for the given PID constants (Talons can
	 *            store multiple sets of PID constants and change from profile
	 *            to profile).
	 */
	public PIDConstants(double kP, double kI, double kD, double kF, double period, double izone, double closedLoopRamp,
			int profile) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.period = period;
		this.izone = izone;
		this.closedLoopRamp = closedLoopRamp;
		this.profile = profile;
	}

	/**
	 * Creates a new {@link PIDConstants} object.
	 *
	 * @param kP
	 *            The Proportional constant for a PIDController.
	 * @param kI
	 *            The Integral constant for a PIDController.
	 * @param kD
	 *            The Differential constant for a PIDController.
	 * @param kF
	 *            The Feedforward constant for a PIDController.
	 * @param izone
	 *            the izone.
	 * @param period
	 *            The loop time for a PIDController (in milliseconds).
	 */
	public PIDConstants(double kP, double kI, double kD, double kF, double izone, double period) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.izone = izone;
		this.period = period;
	}

	/**
	 * Creates a new {@link PIDConstants} object.
	 *
	 * @param kP
	 *            The Proportional constant for a PIDController.
	 * @param kI
	 *            The Integral constant for a PIDController.
	 * @param kD
	 *            The Differential constant for a PIDController.
	 * @param kF
	 *            The Feedforward constant for a PIDController.
	 * @param period
	 *            The loop time for a PIDController (in milliseconds).
	 */
	public PIDConstants(double kP, double kI, double kD, double kF, double period) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.period = period;
	}

	/**
	 * Creates a new {@link PIDConstants} object.
	 *
	 * @param kP
	 *            The Proportional constant for a PIDController.
	 * @param kI
	 *            The Integral constant for a PIDController.
	 * @param kD
	 *            The Differential constant for a PIDController.
	 */
	public PIDConstants(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
}
