/**
 * Copyright (c) 2012 Precise Path Robotics, Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;

/**
 * A Hi Technic accelerometer designed for use with LEGO.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class HiTechnicAccel extends I2C {

	private static final byte kAddress = 0x02;
	private static final byte kDataRegister = 0x42;
	private static final double kGsPerLSB = 1.0 / 200;

	public static class AllAxes {
		public double XAxis;
		public double YAxis;
		public double ZAxis;
	}

	/**
	 * Create an instance of a Hi Technic Accelerometer class on the given
	 * module.
	 *
	 * @param moduleNumber The slot of the digital module that the sensor is
	 * plugged into.
	 */
	public HiTechnicAccel(int moduleNumber) {
		super(DigitalModule.getInstance(moduleNumber), kAddress);
	}

	/**
	 * Create an instance of a Hi Technic Accelerometer class on the default
	 * module.
	 */
	public HiTechnicAccel() {
		this(getDefaultDigitalModule());
	}

	private double accelFromBytes(short high, byte low) {
		// Sensor is 10-bit two's complement numbers.
		// The 8 MSB are on the high byte, the 2 LSB are the LSB on the low byte
		// [pseudo-code:] (high << 2)|low
		// The above example would have 6a few 0s padded to the left, screwing
		// up an interpretation of the result. Casting to a short (above),
		// and multiplying by 4 will do left shift while keeping the sign safe.
		return ((high * 4) + low) * kGsPerLSB;
	}

	/**
	 * Get the acceleration of all axes in Gs.
	 *
	 * @return Acceleration measured on all axes of the ADXL345 in Gs.
	 */
	public AllAxes getAccelerations() {
		AllAxes data = new AllAxes();
		byte[] rawData = new byte[6];
		read(kDataRegister, rawData.length, rawData);

		data.XAxis = accelFromBytes(rawData[0], rawData[3]);
		data.YAxis = accelFromBytes(rawData[1], rawData[4]);
		data.ZAxis = accelFromBytes(rawData[2], rawData[5]);
		return data;
	}
}
