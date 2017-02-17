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
package org.mckenzierobotics.y2012.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.mckenzierobotics.lib.robot.*;
import org.mckenzierobotics.y2012.commands.CheckCompressor;

/**
 * Compressor Subsystem, source-compatible with WPILib Compressor. The
 * Compressor object is designed to handle the operation of the compressor,
 * pressure sensor and relay for a FIRST robot pneumatics system. The Compressor
 * object starts a task which runs in the background and periodically polls the
 * pressure sensor and operates the relay that controls the compressor.
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 * @author WPI
 */
public class Compressor extends CompressorSubsystem {
	protected void initDefaultCommand() {
		setDefaultCommand(new CheckCompressor());
	}

	public Compressor(int pressureSwitchSlot, int pressureSwitchChannel, int compresssorRelaySlot, int compressorRelayChannel) {
		super(pressureSwitchSlot, pressureSwitchChannel, compresssorRelaySlot, compressorRelayChannel);
	}

	public Compressor(int pressureSwitchChannel, int compressorRelayChannel) {
		super(pressureSwitchChannel, compressorRelayChannel);
	}
}
