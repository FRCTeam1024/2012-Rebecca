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
package org.mckenzierobotics.y2012.commands;

import edu.wpi.first.wpilibj.command.Scheduler;
import org.mckenzierobotics.lib.robot.HiTechnicAccel;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class TeleopDrive extends CommandBase {
	/** Percent G */
	double threshold = 0.1;
	double power = .5;
	boolean ballanced = false;
	
	public TeleopDrive() {
		super();
		requires(chassis);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (oi.ballance.get()) {
			chassis.set(oi.rJoystick.getY(), oi.lJoystick.getY());
		} else {
			chassis.set(-oi.lJoystick.getY(), -oi.rJoystick.getY());
		}
	}
	
	protected boolean isFinished() {
		//return ballanced;
		return false;
	}

	protected void initialize() {}
	protected void end() {}
	protected void interrupted() {}
}
