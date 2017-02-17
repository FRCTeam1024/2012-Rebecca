/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;

/**
 *
 * @author luke
 */
public class AnalogIO {

	private final int port;
	private static final double max_volts = 3.3;
	private boolean invert;
	private double low = 0;
	private double high = 1;
	
	public AnalogIO(int port, boolean invert) {
		this.port = port;
		this.invert = invert;
	}
	
	public AnalogIO(int port) {
		this(port, false);
	}
	
	public void setRange(double low, double high) {
		this.low = low;
		this.high = high;
	}

	public double get() {
		double volts;
		try {
			volts = DriverStation.getInstance().getEnhancedIO().getAnalogIn(port);
		} catch (DriverStationEnhancedIO.EnhancedIOException ex) {
			volts = 0;
		}
		double percent = volts/max_volts;
		return ((high-low)*percent)+low;
	}
}
