/**
 * CommandBase - defines robot hardware; superclass of all commands.
 * 
 * Copyright (c) FIRST 2008. All Rights Reserved.
 * Open Source Software - may be modified and shared by FRC teams. The code
 * must be accompanied by the FIRST BSD license file in the root directory of
 * the project.
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

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The base for all commands, also the class defining robot hardware/config.
 * All atomic commands should subclass CommandBase. CommandBase stores creates
 * and stores each control system.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public abstract class CommandBase extends Command {

	private boolean has_subsystems = true;
	protected synchronized void requires(Subsystem subsystem) {
		if (subsystem == null) {
			has_subsystems = false;
		}
		super.requires(subsystem);
	}
	
	public synchronized void start() {
		if (has_subsystems) { super.start(); }
	}

	/**
	 * Instantiate a CANJaguar, handling exceptions.
	 * 
	 * @param id CANJaguar ID to use.
	 * @return the CANJaguar
	 */
	public static CANJaguar getJaguar(int id) {
		CANJaguar ret = null;
		try {
			ret = new CANJaguar(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public CommandBase() { super(); }
	public CommandBase(double timeout) { super(timeout); }
	public CommandBase(String name) { super(name); }
	public CommandBase(String name, double timeout) { super(name, timeout); }
}
