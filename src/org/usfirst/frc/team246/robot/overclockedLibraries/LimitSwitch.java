package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {
	private int maxSwitchCount;
	private int onSwitchCount = 0;
	private int offSwitchCount = 0;

	private boolean isOn = false;

	public LimitSwitch(int channel, int maxSwitchCount) {
		super(channel);
		this.maxSwitchCount = maxSwitchCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.DigitalInput#get()
	 */
	@Override
	public boolean get() {

		if (super.get()) {
			onSwitchCount++;
			if (onSwitchCount < maxSwitchCount) {
				isOn = isOn ? true : false;
			} else {
				isOn = true;
				onSwitchCount = 0;
			}
		} else {
			offSwitchCount++;
			if (offSwitchCount < maxSwitchCount) {
				isOn = !isOn ? false : true;
			} else {
				isOn = false;
				offSwitchCount = 0;
			}
		}
		return isOn;
	}
}
