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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class AutoMode extends CommandGroup {

	public static final double shoot_foul = -.20;
	public static final double shoot_side = -.03;//
	public static final double shoot_key = -.10;
	public static final double shoot_bridgesquared = .12;
	public static final double shoot_bridge = .12;//
	public boolean feed = false;
	public boolean main = false;
	public boolean grab = false;

	public void setShooter(double diff) {
		addSequential(new ShooterCommand(diff));
	}

	public void setRollers(boolean f, boolean m, boolean g) {
		feed = f;
		main = m;
		grab = g;
		addSequential(new RollerCommand(feed ? -1 : 0, main ? -1 : 0, grab ? -1 : 0));
	}

	public void sleep(double time) {
		addSequential(new WaitCommand(time));
	}

	public void ramUp() {
		addSequential(new CoopertitionRamCommand(false));
	}

	public void ramDown() {
		addSequential(new CoopertitionRamCommand(true));
	}

	public void drive(double l, double r) {
		addSequential(new DriveCommand(l, r));
	}


	public void goToBridgeFromKey() {
		setShooter(shoot_bridge);
		ramDown();
		drive(.6, .6);
		sleep(1);
		drive(.3, .3);
		sleep(2);
		drive(0, 0);
	}

	public void goToBridgeFromFoul() {
		setShooter(shoot_bridge);
		ramDown();
		drive(.6, .6);
		sleep(2.5);
		drive(.3, .3);
		sleep(2);
		drive(0, 0);
	}

	public void shoot(double time) {
		setRollers(true, true, grab);
		sleep(time);
		setRollers(true, false, grab);
	}

	public void backOff() {
		setShooter(shoot_bridge);
		drive(-.4, -.4);
		sleep(.5);
		drive(0, 0);
	}

	public void getBalls() {
		drive(.4, .4);
		sleep(.75);
		drive(0, 0);
	}

	private class ResetPIDCommand extends EmptyCommand {
		public void execute() {
			shooterT.getPID().reset();
			shooterB.getPID().reset();
		}
	}

	public String getDescription() {
		return "Nothing";
	}

	public AutoMode() {
		double delay = .25;
		sleep(delay/2);
		addSequential(new ResetPIDCommand());
		sleep(delay/2);
	}
}
