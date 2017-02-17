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

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class RollerCommand extends CommandBase {
	private final int f, m, g;
	public RollerCommand(int feed, int main, int grab) {
		super();
		requires(rollerFeed);
		requires(rollerMain);
		requires(rollerGrab);
		f = feed;
		m = main;
		g = grab;
	}

	protected void execute() {
		rollerFeed.set(f);
		rollerMain.set(m);
		rollerGrab.set(g);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void initialize() {}
	protected void end() {}
	protected void interrupted() {}
}
