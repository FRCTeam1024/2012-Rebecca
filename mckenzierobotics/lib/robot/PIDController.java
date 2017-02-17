/**
 * Copyright (c) 2011, 2012 Luke Shumaker
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
 */
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;

/**
 * TODO: Write JavaDocs
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class PIDController extends SendablePIDController implements SpeedController {
	private boolean autodisable = false;
	/**
	 * Default to true, so if we don't use autodisable, we don't enable when we
	 * shouldn't, as we will think it's already enabled.
	 */
	private boolean enabled = true;
	public PIDController(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output) {
		super(Kp, Ki, Kd, source, output);
	}
	public PIDController(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period) {
		super(Kp, Ki, Kd, source, output, period);
	}
	
	public PIDController(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, boolean autodisable) {
		super(Kp, Ki, Kd, source, output);
		this.autodisable = autodisable;
		enabled = this.isEnable();
	}
	public PIDController(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output, double period, boolean autodisable) {
		super(Kp, Ki, Kd, source, output, period);
		this.autodisable = autodisable;
		enabled = this.isEnable();
	}
	
	public void setSetpoint(double output) {
		if ((output == 0) && autodisable) {
			disable();
			enabled = false;
		} else {
			if (!enabled) {
				enable();
				enabled = true;
			}
			super.setSetpoint(output);
		}
	}
	
	public void pidWrite(double output) {
		setSetpoint(output);
	}
	
	public void set(double output) {
		setSetpoint(output);
	}
	
	/**
	 * Don't use this; it is leaking up from CANJaguar
	 * @param output
	 * @param syncGroup 
	 */
	public void set(double output, byte syncGroup) {
		setSetpoint(output);
	}
}
