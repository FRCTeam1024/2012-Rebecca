/**
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

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Compressor Subsystem, source-compatible with WPILib Compressor.
 * The Compressor object is designed to handle the operation of the compressor,
 * pressure sensor and relay for a FIRST robot pneumatics system. The Compressor
 * object starts a task which runs in the background and periodically polls the
 * pressure sensor and operates the relay that controls the compressor.
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 * @author WPI
 */
public class CompressorSubsystem extends Subsystem {
	edu.wpi.first.wpilibj.Compressor obj;

	protected void initDefaultCommand() {}

	/**
	 * Compressor constructor.
     * Given a fully specified relay channel and pressure switch channel,
	 * initialize the Compressor object.
     *
     * You MUST start the compressor by calling the start() method.
     *
     * @param pressureSwitchSlot The module that the pressure switch is attached to.
     * @param pressureSwitchChannel The GPIO channel that the pressure switch is attached to.
     * @param compresssorRelaySlot The module that the compressor relay is attached to.
     * @param compressorRelayChannel The relay channel that the compressor relay is attached to.
     */
	public CompressorSubsystem(int pressureSwitchSlot, int pressureSwitchChannel, int compresssorRelaySlot, int compressorRelayChannel) {
		obj = new edu.wpi.first.wpilibj.Compressor(
				pressureSwitchSlot,
				pressureSwitchChannel,
				compresssorRelaySlot,
				compressorRelayChannel);
	}

	/**
     * Compressor constructor.
     * Given a relay channel and pressure switch channel (both in the default
	 * digital module), initialize the Compressor object.
     *
     * You MUST start the compressor by calling the start() method.
     *
     * @param pressureSwitchChannel The GPIO channel that the pressure switch is attached to.
     * @param compressorRelayChannel The relay channel that the compressor relay is attached to.
     */
	public CompressorSubsystem(int pressureSwitchChannel, int compressorRelayChannel) {
		obj = new edu.wpi.first.wpilibj.Compressor(
				pressureSwitchChannel,
				compressorRelayChannel);
	}

	/**
     * Delete the Compressor object.
     * Delete the allocated resources for the compressor and kill the compressor
	 * task that is polling the pressure switch.
     */
    public void free() {
		obj.free();
	}

	/**
     * Operate the relay for the compressor.
     * Change the value of the relay output that is connected to the compressor
	 * motor. This is only intended to be called by the internal polling thread.
     * @param relayValue the value to set the relay to
     */
    public void setRelayValue(Relay.Value relayValue) {
		obj.setRelayValue(relayValue);
	}

	/**
     * Get the pressure switch value.
     * Read the pressure switch digital input.
     *
     * @return The current state of the pressure switch.
     */
    public boolean getPressureSwitchValue() {
		return obj.getPressureSwitchValue();
	}

	/**
     * Start the compressor.
     * This method will allow the polling loop to actually operate the
	 * compressor. The compressor is stopped by default and won't operate until
	 * starting it.
     */
    public void start() {
		obj.start();
	}

	/**
     * Stop the compressor.
     * This method will stop the compressor from turning on.
     */
    public void stop() {
		obj.stop();
	}

	/**
     * Get the state of the enabled flag.
     * Return the state of the enabled flag for the compressor and pressure
	 * switch combination.
     *
     * @return The state of the compressor thread's enable flag.
     */
    public boolean enabled() {
		return obj.enabled();
	}
}
