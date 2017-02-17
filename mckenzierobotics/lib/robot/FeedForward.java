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
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public abstract class FeedForward implements PIDOutput {
	private class PIDTrigger implements PIDOutput {
		private FeedForward ff;
		public PIDTrigger(FeedForward ff) {
			this.ff = ff;
		}
		public void pidWrite(double output) {
			ff.update(output);
		}
	}
	private PIDController pid;
	private PIDOutput pidOutput;
	public FeedForward(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output) {
		pidOutput = output;
		pid = new PIDController(Kp, Ki, Kd, source, new PIDTrigger(this));
	}
	public FeedForward(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period) {
		pidOutput = output;
		pid = new PIDController(Kp, Ki, Kd, source, new PIDTrigger(this), period);
	}
	
	public FeedForward(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, boolean autodisable) {
		pidOutput = output;
		pid = new PIDController(Kp, Ki, Kd, source, new PIDTrigger(this), autodisable);
	}
	public FeedForward(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period, boolean autodisable) {
		pidOutput = output;
		pid = new PIDController(Kp, Ki, Kd, source, new PIDTrigger(this), period, autodisable);
	}
	
	public PIDController getPID() {
		return pid;
	}
	
	public void pidWrite(double output) {
		pid.setSetpoint(output);
	}
	
	public void update(double pidResult) {
		if (pid.isEnable()) {
			double ffResult = calculate(pid.getSetpoint());
			pidOutput.pidWrite(ffResult+pidResult);
		} else {
			pidOutput.pidWrite(0);
		}
	}
	
	public abstract double calculate(double setpoint);
}
