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
package org.mckenzierobotics.y2012.lib;

import edu.wpi.first.wpilibj.ADXL345_I2C;

/**
 * The ADXL345 digital accelerometer, on the I2C interface.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class I2CAccelerometer extends ADXL345_I2C {
	/**
	 * Constructor.
	 * 
	 * @param moduleNumber The number of the digital module to use for this input.
	 * @param range The range (+ or -) that the accelerometer will measure.
	 */
	public I2CAccelerometer(int moduleNumber, DataFormat_Range range) {
		super(moduleNumber, range);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param range The range (+ or -) that the accelerometer will measure.
	 */
	public I2CAccelerometer(DataFormat_Range range) {
		this(getDefaultDigitalModule(), range);
	}
}
