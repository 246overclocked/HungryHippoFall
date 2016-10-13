package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The Class Toggle runs one command when the trigger is true and another when
 * false.  Toggle is an abstract class that need to be implemented in OI by overriding the get and 
 * getToggler methods.
 */
public abstract class Toggle extends Trigger {

	/**
	 * Gets the toggler.
	 *
	 * @return the toggler
	 */
	public abstract boolean getToggler();

	/** The changed. */
	boolean changed = false;

	/**
	 * Toggle.
	 *
	 * @param trueCommand
	 *            the command that runs when the toggle is in the true state
	 * @param falseCommand
	 *            the command that runs when the toggle is in the false state
	 */
	public void toggle(final Command trueCommand, final Command falseCommand) {
		new Trigger() {

			@Override
			public boolean get() {
				boolean result = Toggle.this.get() && Toggle.this.getToggler() && !changed;
				if (Toggle.this.get() && Toggle.this.getToggler()) {
					changed = true;
				}
				if (!Toggle.this.get()) {
					changed = false;
				}
				return result;
			}
		}.whenActive(trueCommand);

		new Trigger() {

			@Override
			public boolean get() {
				boolean result = Toggle.this.get() && !Toggle.this.getToggler() && !changed;
				if (Toggle.this.get() && !Toggle.this.getToggler()) {
					changed = true;
				}
				if (!Toggle.this.get()) {
					changed = false;
				}
				return result;
			}
		}.whenActive(falseCommand);
	}
}
