/**
 * Copyright (c) 2012 Precise Path Robotics, Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */

package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class LimitedPIDOutput implements PIDOutput {
	PIDOutput o;
	double m;
	double prev_rate = 0;
	double prev_time = 0;
	
	public LimitedPIDOutput(PIDOutput out, double maxChange) {
		o = out;
		m = maxChange;
	}
	
	public void pidWrite(double rate) {
		double time = Timer.getFPGATimestamp();
		double dTime = time-prev_time;
		double dRate = rate-prev_rate;
		if (Math.abs(dRate/dTime)>m) {
			int sign = (dRate<0?-1:1);
			rate = prev_rate+(m*dTime*sign);
		}
		o.pidWrite(rate);
	}
}
