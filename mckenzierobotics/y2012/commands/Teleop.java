package org.mckenzierobotics.y2012.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.mckenzierobotics.y2012.subsystems.*;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class Teleop extends CommandGroup {
	
	public Teleop() {
		addParallel(new ShootWithOI());
		//addParallel(new ShootWithDashboard());
		//addParallel(new TuneWithJoystick());
		
		//addParallel(new CameraTrack());
		
		addParallel(new TeleopDrive());
		addParallel(new RollWithButtons());
	}
}
