package org.mckenzierobotics.y2012.subsystems;

import org.mckenzierobotics.y2012.commands.UpdateLCD;

public class LCD extends org.mckenzierobotics.lib.robot.LCDSubsystem {
	public void initDefaultCommand() {
		setDefaultCommand(new UpdateLCD());
	}
}
