/**
 * CommandFramework base class for FRC 1024.
 * 
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

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public abstract class CommandRobot extends IterativeRobot {

	private Command autonomousCommand;
	private Command teleopCommand;

	public abstract Command getAutonomous();
	public abstract Command getTeleop();

	public void robotInit() {
		autonomousCommand = getAutonomous();
		teleopCommand = getTeleop();
	}

	public void autonomousInit() {
		autonomousCommand.start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		autonomousCommand.cancel();
		teleopCommand.start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void disabledInit() {
		autonomousCommand.cancel();
		teleopCommand.cancel();
	}
}
