package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The Class LogitechF310.
 */
public class LogitechF310 extends Joystick246 {

	/** The left joystick x axis. */
	protected int leftXAxis = 0;

	/** The left joystick y axis. */
	protected int leftYAxis = 1;

	/** The right joystick x axis. */
	protected int rightXAxis = 4;

	/** The right joystick y axis. */
	protected int rightYAxis = 5;

	/** The left trigger axis. */
	protected int leftTriggerAxis = 2;

	/** The right trigger axis. */
	protected int rightTriggerAxis = 3;

	protected int x = 3;

	protected int y = 4;

	protected int a = 1;

	protected int b = 2;

	protected int lb = 5;

	protected int rb = 6;

	protected int back = 7;

	protected int start = 8;

	protected int leftStick = 9;

	protected int rightStick = 10;

	/**
	 * Instantiates a new logitech f310 controller.
	 *
	 * @param port
	 *            the port
	 */
	public LogitechF310(int port) {
		super(port);
	}

	/**
	 * Gets the left joystick x axis.
	 *
	 * @return the left joystick x axis
	 */
	public double getLeftXAxis() {
		if (Math.abs(getRawAxis(leftXAxis)) > .05)
			return getRawAxis(leftXAxis);
		else
			return 0;
	}

	/**
	 * Gets the left joystick y axis.
	 *
	 * @return the left joystick y axis
	 */
	public double getLeftYAxis() {
		if (Math.abs(getRawAxis(leftYAxis)) > .05)
			return -getRawAxis(leftYAxis);
		else
			return 0;
	}

	/**
	 * Gets the right joystick x axis.
	 *
	 * @return the right joystick x axis
	 */
	public double getRightXAxis() {
		if (Math.abs(getRawAxis(rightXAxis)) > .05)
			return getRawAxis(rightXAxis);
		else
			return 0;
	}

	/**
	 * Gets the right joystick y axis.
	 *
	 * @return the right joystick y axis
	 */
	public double getRightYAxis() {
		if (Math.abs(getRawAxis(rightYAxis)) > .05)
			return -getRawAxis(rightYAxis);
		else
			return 0;
	}

	/**
	 * Gets the left trigger axis.
	 *
	 * @return the left trigger axis
	 */
	public double getLeftTriggerAxis() {
		if (Math.abs(getRawAxis(leftTriggerAxis)) > .05)
			return getRawAxis(leftTriggerAxis);
		else
			return 0;
	}

	/**
	 * Gets the right trigger axis.
	 *
	 * @return the right trigger axis
	 */
	public double getRightTriggerAxis() {
		if (Math.abs(getRawAxis(rightTriggerAxis)) > .05)
			return getRawAxis(rightTriggerAxis);
		else
			return 0;
	}

	/**
	 * Gets the left joystick magnitude.
	 *
	 * @return the left joystick magnitude
	 */
	public double getLeftMagnitude() {
		return Math.sqrt(Math.pow(getLeftXAxis(), 2) + Math.pow(getLeftYAxis(), 2));
	}

	/**
	 * Gets the left joystick angle.
	 *
	 * @return the left joystick angle
	 */
	public double getLeftAngle() {
		return Math.toDegrees(Math.atan2(getLeftXAxis(), -getLeftYAxis()));
	}

	/**
	 * Gets the right joystick magnitude.
	 *
	 * @return the right joystick magnitude
	 */
	public double getRightMagnitude() {
		return Math.sqrt(Math.pow(getRightXAxis(), 2) + Math.pow(getRightYAxis(), 2));
	}

	/**
	 * Gets the right joystick angle.
	 *
	 * @return the right joystick angle
	 */
	public double getRightAngle() {
		return Math.toDegrees(Math.atan2(getRightXAxis(), -getRightYAxis()));
	}

	/**
	 * Gets the X button.
	 *
	 * @return the X button
	 */
	public Button getX2() {
		return new JoystickButton(this, x);
	}

	/**
	 * Gets the Y button.
	 *
	 * @return the Y button
	 */
	public Button getY2() {
		return new JoystickButton(this, y);
	}

	/**
	 * Gets the A button.
	 *
	 * @return the A button
	 */
	public Button getA() {
		return new JoystickButton(this, a);
	}

	/**
	 * Gets the B button.
	 *
	 * @return the B button
	 */
	public Button getB() {
		return new JoystickButton(this, b);
	}

	/**
	 * Gets the left bumper.
	 *
	 * @return the left bumper
	 */
	public Button getLB() {
		return new JoystickButton(this, lb);
	}

	/**
	 * Gets the right bumper
	 *
	 * @return the right bumper
	 */
	public Button getRB() {
		return new JoystickButton(this, rb);
	}

	/**
	 * Gets the back button.
	 *
	 * @return the back button
	 */
	public Button getBack() {
		return new JoystickButton(this, back);
	}

	/**
	 * Gets the start button.
	 *
	 * @return the start button
	 */
	public Button getStart() {
		return new JoystickButton(this, start);
	}

	/**
	 * Gets the button representing whether the left stick is pushed down.
	 *
	 * @return the left stick pushed down button
	 */
	public Button getLeftStick() {
		return new JoystickButton(this, leftStick);
	}

	/**
	 * Gets the button representing whether the right stick is pushed down.
	 *
	 * @return the right stick pushed down button
	 */
	public Button getRightStick() {
		return new JoystickButton(this, rightStick);
	}

	/**
	 * Gets the left trigger.
	 *
	 * @return the left trigger button
	 */
	public Button getLT() {
		return new Button() {

			@Override
			public boolean get() {
				return getLeftTriggerAxis() > .5;
			}
		};
	}

	/**
	 * Gets the right trigger.
	 *
	 * @return the right trigger button
	 */
	public Button getRT() {
		return new Button() {

			@Override
			public boolean get() {
				return getRightTriggerAxis() > .5;
			}
		};
	}

	/**
	 * Gets the up button on the Dpad.
	 *
	 * @return the up arrow on the Dpad
	 */
	public Button getUp() {
		return new Button() {

			@Override
			public boolean get() {
				return getPOV() == 0;
			}
		};
	}

	/**
	 * Gets the right button on the Dpad.
	 *
	 * @return the right arrow on the Dpad
	 */
	public Button getRight() {
		return new Button() {

			@Override
			public boolean get() {
				return getPOV() == 90;
			}
		};
	}

	/**
	 * Gets the down button on the Dpad.
	 *
	 * @return the down arrow on the Dpad
	 */
	public Button getDown() {
		return new Button() {

			@Override
			public boolean get() {
				return getPOV() == 180;
			}
		};
	}

	/**
	 * Gets the left button on the Dpad.
	 *
	 * @return the left arrow on the Dpad
	 */
	public Button getLeft() {
		return new Button() {

			@Override
			public boolean get() {
				return getPOV() == 270;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.Joystick#getDirectionRadians()
	 */
	@Override
	public double getDirectionRadians() {
		return -super.getDirectionRadians();
	}
}
