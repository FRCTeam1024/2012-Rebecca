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

package org.mckenzierobotics.y2012;

import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.Joystick;
import org.mckenzierobotics.lib.robot.AnalogIO;
import org.mckenzierobotics.y2012.commands.*;

public class OI {
    // The following ASCII-art diagrams explain what is where.
	/*
	 * Cypress Breakout Board:
	 *
	 * GND      	GND
	 * -------------------------
	 * P2_7  D16	P2_6  D15
	 * P12_3 D14	P12_2 D13
	 * P6_7  D12	P6_6  D11
	 * P6_5  D10	P6_4  D09
	 * P6_3  D08	P6_2  D07
	 * P6_1  D06	P6_0  D05
	 * P4_7  D04	P4_6  D03
	 * P4_5  D02	P4_4  D01
	 * -------------------------
	 * P0_7  AI8	P0_6  AI7
	 * P0_5  AI6	P0_4  AI5
	 * P0_3  AI4	P0_2  AI3
	 * P0_1  AI2	P0_0  AI1
	 * -------------------------
	 * VDDIO    	VDDIO
	 */
	/*
	 * DriverStation key:
	 *
	 * +-------+---------------------------------------------
	 * |       |
	 * |       | (D02)                              (D01)
	 * | (D10) |
	 * |       |   (D04)                          (D03)
	 * | (D12) |
	 * |       |     (D06)              +---+   (D05)
	 * |       |                        |D15|
	 * |       |              (A02)     |D13|
	 * |       |              H   L     +---+
	 * +-------+--------------------------------------------
	 *         |        t[D08]f             f[D07]t
	 *         |
	 *         +--------------------------------------------
	 */
	
	// Actual code now.
	
	// Trust me, you want to use these JoystickButtons, not the WPIlib ones.
	class LJoystickButton extends Button {
		int n;
		public LJoystickButton(int num) { n = num; }
		public boolean get() { return lJoystick.getRawButton(n); }
	}
	
	class RJoystickButton extends Button {
		int n;
		public RJoystickButton(int num) { n = num; }
		public boolean get() { return rJoystick.getRawButton(n); }
	}
	
	// Declare OI elements here
	public Joystick lJoystick = new Joystick(2);
	public Joystick rJoystick = new Joystick(1);
	public Button
			rollFeedU = new DigitalIOButton(2), rollFeedD = new DigitalIOButton(1),
			rollMainU = new DigitalIOButton(4),	rollMainD = new DigitalIOButton(3),
			rollGrabU = new DigitalIOButton(6),	rollGrabD = new DigitalIOButton(5),
			
			shoot = new DigitalIOButton(8),
			ballance = new DigitalIOButton(7),
			shiftLow = new LJoystickButton(1), shiftHigh = new LJoystickButton(2),
			ramDown  = new RJoystickButton(1), ramUp     = new RJoystickButton(2),
			fixJoysticksButton = new RJoystickButton(7);
	public AnalogIO backspin = new AnalogIO(2);
	
	public OI() {
		backspin.setRange(1, -1);
	 
		ramUp.whenPressed(new EmptyCommand() {
			{ requires(coopertitionram); }
			protected void execute() { coopertitionram.setDown(false); }
		});
		ramDown.whenPressed(new EmptyCommand() {
			{ requires(coopertitionram); }
			protected void execute() { coopertitionram.setDown(true); }
		});
		
		shiftHigh.whenPressed(new EmptyCommand() {
			{ requires(shifter); }
			protected void execute() { shifter.setHigh(true); }
		});
		
		shiftLow.whenPressed(new EmptyCommand() {
			{ requires(shifter); }
			protected void execute() { shifter.setHigh(false); }
		});
		
		fixJoysticksButton.whenPressed(new EmptyCommand() {
			protected void execute() {
				Joystick tempJoy = oi.lJoystick;
				oi.lJoystick = oi.rJoystick;
				oi.rJoystick = tempJoy;
			}
		});
	}
}
