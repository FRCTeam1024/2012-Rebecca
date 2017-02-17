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
 * @author Parker Smith
 */
package org.mckenzierobotics.y2012.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Parker Smith
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class Shooter extends Subsystem {
	double d = 0;
	private double tp = 0;
	private double bp = 1;
	private final PIDOutput t;
	private final PIDOutput b;
	private final double m_rate;
	private boolean disabled = false;

	/**
	 *
	 * @param top
	 * @param bottom
	 * @param maxRate The maximum rate of the SpeedControllers, in whatever
	 * units top and bottom use; this class is agnostic to it.
	 */
	public Shooter(PIDOutput top, PIDOutput bottom, double maxRate) {
		t = top;
		b = bottom;
		m_rate = maxRate;
	}

	public void initDefaultCommand() {
	}

	private void update() {
		if (disabled) {
			t.pidWrite(0);
			b.pidWrite(0);
		} else {
			t.pidWrite(tp * m_rate);
			b.pidWrite(bp * m_rate);
		}
	}

	public synchronized void setDifference(double diff) {
		d = diff;
		tp = Math.max(d, 0.00000001);// not 0
		bp = 1 + Math.min(d, 0);
		update();
	}

	public synchronized double getDifference() {
		return d;
	}

	public synchronized void disable() {
		disabled = true;
		update();
	}

	public synchronized void enable() {
		disabled = false;
		update();
	}
}
