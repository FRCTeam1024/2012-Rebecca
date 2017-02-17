/**
 * Copyright (c) 2010, 2012 Luke Shumaker
 * Copyright (c) 2010 Michael Andrzejewski
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FIRST nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY FIRST AND CONTRIBUTORS``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY NONINFRINGEMENT AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL FIRST OR CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 * @author Michael Andrzejewski
 */
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * High-er level driver for the Driver Station LCD.
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 * @author Michael Andrzejewski
 */
public class LCDSubsystem extends Subsystem {
	private final DriverStationLCD lcd = DriverStationLCD.getInstance();

	/**
	 * The LCD subsystem does not run a default command.
	 */
	public void initDefaultCommand() {}
	
	/**
	 * The contents of the 6 lines, each index of the array is an LCD line.
	 * You can access these directly, just call update() when you are done.
	 *
	 * @see #update()
	 */
	public String lines[]={"0:","1:","2:","3:","4:","5:"};
	private static final DriverStationLCD.Line lineKeys[] = {
		DriverStationLCD.Line.kMain6,
		DriverStationLCD.Line.kUser2,
		DriverStationLCD.Line.kUser3,
		DriverStationLCD.Line.kUser4,
		DriverStationLCD.Line.kUser5,
		DriverStationLCD.Line.kUser6,
	};
	
	private String spaces;
	
	/**
	 * Initialize the LCD class.
	 */
	public LCDSubsystem() {
		// create a string full of spaces, the length of an LCD row.
		char spaceA[]=new char[DriverStationLCD.kLineLength];
		for (int i=0;i<DriverStationLCD.kLineLength;i++) {
			spaceA[i]=' ';
		}
		spaces = new String(spaceA);
	}
	
	/**
	 * Print a new line, and scroll the old lines.
	 * This calls update() for you.
	 *
	 * @see #update()
	 * @param line
	 */
	public void println(String line) {
		for (int i=0; i<(lines.length-1); i++)
			lines[i]=lines[i+1];
		lines[lines.length-1]=line;
		update();
	}
	
	/**
	 * Updates the screen based on the lines[] variable.
	 *
	 * @see #lines
	 */
	public void update() {
		for (int i=0;i<(lines.length);i++) {
			if (lines[i].length()<DriverStationLCD.kLineLength) {
				lines[i]=lines[i]+spaces.substring(lines[i].length());
			}
			lcd.println(lineKeys[i], 1, lines[i]);
		}
		lcd.updateLCD();
	}
}
