package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * 246 Overclocked utility functions for analog input
 */
public class AnalogIn implements PIDSource, LiveWindowSendable {

	/** onRIO On or off the RoboRIO */
	public boolean onRIO;

	/** The analog input. */
	public AnalogInput input;

	/** The current value of the input. */
	public double currentVal = 0;

	/** The source type of the input. */
	private PIDSourceType sourceType = PIDSourceType.kDisplacement;

	/**
	 * Instantiates a new analog input.
	 *
	 * @param channel
	 *            the input channel
	 * @param onRIO
	 *            On the RoboRIO or not
	 */
	public AnalogIn(int channel, boolean onRIO) {
		this.onRIO = onRIO;
		if (onRIO) {
			input = new AnalogInput(channel);
			input.setAverageBits(3);
		}
	}

	/**
	 * Gets the average sampled value if on the RIO otherwise gets the current
	 * value of the input.
	 *
	 * @return average or most recent sample
	 */
	public double get() {
		if (onRIO) {
			return input.getAverageVoltage();
		} else {
			return currentVal;
		}
	}

	/**
	 * Update the current value
	 *
	 * @param val
	 *            the value
	 */
	public void updateVal(double val) {
		currentVal = val;
	}

	private ITable m_table;

	/**
	 * {@inheritDoc}
	 */
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Value", get());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ITable getTable() {
		return m_table;
	}

	/**
	 * Analog Channels don't have to do anything special when entering the
	 * LiveWindow. {@inheritDoc}
	 */
	public void startLiveWindowMode() {
	}

	/**
	 * Analog Channels don't have to do anything special when exiting the
	 * LiveWindow. {@inheritDoc}
	 */
	public void stopLiveWindowMode() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
	 */
	@Override
	public double pidGet() {
		return get();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.Sendable#getSmartDashboardType()
	 */
	@Override
	public String getSmartDashboardType() {
		return "Analog Input";
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.wpi.first.wpilibj.PIDSource#setPIDSourceType(edu.wpi.first.wpilibj.
	 * PIDSourceType)
	 */

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		sourceType = pidSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDSource#getPIDSourceType()
	 */
	@Override
	public PIDSourceType getPIDSourceType() {
		return sourceType;
	}

}
