package org.usfirst.frc.team246.robot.overclockedLibraries;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class AHRS246 extends AHRS {

	public AHRS246(Port serial_port_id) {
		super(serial_port_id);
	}
	
	public AHRS246(SPI.Port spi_port_id) {
		super(spi_port_id);
	}
	
	@Override
	public float getYaw() {
		return -super.getYaw();
	}
}
