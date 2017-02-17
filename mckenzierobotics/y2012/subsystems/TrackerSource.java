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

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import org.mckenzierobotics.y2012.lib.NITracker;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class TrackerSource extends Subsystem {

	public final PIDSource x;
	public final PIDSource y;

	private final AxisCamera camera;
	private final NITracker tracker = new NITracker();
	
	private double xpos = 0;
	private double ypos = 0;
	private int targets = 0;
	
	public TrackerSource(AxisCamera c) {
		camera = c;
		x = new PIDSource() { public double pidGet() {
			TrackerSource.this.update();
			return TrackerSource.this.getX();
		}};
		y = new PIDSource() { public double pidGet() {
			TrackerSource.this.update();
			return TrackerSource.this.getY();
		}};
	}

	public void initDefaultCommand() {}
	
	public double getX() { return xpos; }
	public double getY() { return ypos; }
	public int getNumTargets() { return targets; }
	
	public boolean update() {
		if (camera.freshImage()) {
			edu.wpi.first.wpilibj.Watchdog.getInstance().feed();
			try {
				ColorImage image = camera.getImage();
				tracker.processImage(image);
				ParticleAnalysisReport[] reports = tracker.processImage(image);
				image.free();
				
				targets = reports.length;
				System.out.println(targets + "  " + Timer.getFPGATimestamp());
				
				if (targets > 0) {
					ParticleAnalysisReport target = reports[0];
					xpos = target.center_mass_x_normalized;
					ypos = target.center_mass_y_normalized;
				}
			} catch (Exception e) {
				targets = 0;
				xpos = ypos = 0;
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
