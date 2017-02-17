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
package org.mckenzierobotics.y2012.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class FeedRoller extends Subsystem {
	private final PIDOutput m;
	private final double scale;
	public FeedRoller(PIDOutput motor, double scale) {
		m = motor;
		this.scale = scale;
	}
	public void initDefaultCommand() {}
	
	public void set(int dir) {
		m.pidWrite(dir*scale);
	}
}
