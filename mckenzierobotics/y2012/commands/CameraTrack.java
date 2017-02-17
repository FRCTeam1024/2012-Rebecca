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

import com.sun.squawk.util.MathUtils;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class CameraTrack extends CommandBase {
	
	public CameraTrack() {
		super();
		requires(cameraTracker);
	}
	
	public void center() {
		cameraServoX.set(.5);
		cameraServoY.set(0);
	}

	protected void initialize() {
		center();
	}
	
	/**
	 * 
	 * @param fov The field of vision of the camera in degrees
	 * @param percent how far over in the camera image
	 * @return The angle in degrees
	 */
	private double getAngle(double fov, double percent) {
		fov = Math.toRadians(fov);
		double angle = MathUtils.asin(Math.sin(fov/2)*percent);
		return Math.toDegrees(angle);
	}

	protected void execute() {
		if (cameraTracker.update()) {
			if (cameraTracker.getNumTargets()>0) {
				cameraServoX.pidWrite(getAngle(47, cameraTracker.getX()));
				cameraServoY.pidWrite(getAngle(30, cameraTracker.getY()));
			} else {
				center();
			}
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {}
	protected void interrupted() {}
}
