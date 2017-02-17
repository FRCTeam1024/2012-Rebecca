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
package org.mckenzierobotics.y2012.lib;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mckenzierobotics.lib.robot.*;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class PIDTunerZieglerNichols {
	private final PIDController m_pid;
	private int m_stage;
	private double Ku, Pu, Kp, Ki, Kd;
	
	public PIDTunerZieglerNichols(PIDController controller,
			double Ku, double Pu, int stage) {
		m_pid = controller;
		this.Ku = Ku;
		this.Pu = Pu;
		m_stage = stage;
		
		m_threshold = 0;
		m_avgSample = null;
		m_avgPeriod = null;
		
		calculatePID();
		update();
	}
	
	public void setU(double U) {
		Ku = U;
		update();
	}
	public void setPeriod(double period) {
		Pu = period;
		update();
	}
	public double getPeriod() { return Pu; }
	public double getP() { return Kp; }
	public double getI() { return Ki; }
	public double getD() { return Kd; }
	
	private void update() {
		calculatePID();
		m_pid.setPID(Kp, Ki, Kd);
	}
		
	private void calculatePID() {
		switch (m_stage) {
			default:
				Kp = Ku;
				Ki = 0;
				Kd = 0;
				break;
			case 1:
				Kp = .5 * Ku;
				Ki = 0;
				Kd = 0;
				break;
			case 2:
				Kp = .45 * Ku;
				Ki = 1.2 * Kp / Pu;
				Kd = 0;
				break;
			case 3:
				Kp = .6 * Ku;
				Ki = 2 * Kp / Pu;
				Kd = Kp * Pu / 8;
				break;
		}
	}
	
	/**************************************************************************\
	 * Stage 0 (tuning)                                                       *
	\**************************************************************************/
	
	public PIDTunerZieglerNichols(PIDController controller, double threshold, int samples, int peaks) {
		m_pid = controller;
		m_stage = 0;
		
		m_threshold = threshold;
		m_avgSample = new RollingAvg(samples);
		m_avgPeriod = new RollingAvg(peaks);
		
		m_timer.start();
		
		update();
	}
		
	private final Timer m_timer = new Timer();
	private final double m_threshold;
	private final RollingAvg m_avgSample;
	private final RollingAvg m_avgPeriod;
	
	/** Currently in a peak? */
	private boolean peak = false;
	/** If so, when did it start? */
	private double startPeak = 0;
	/** When was the last peak? */
	private double lastPeak = 0;
	
	public void analyzeError() {
		double error = m_pid.getError();
		m_avgSample.push(error);
		if (!peak && (error > (m_avgSample.get()*m_threshold))) {
			peak = true;
			startPeak = m_timer.get();
		}
		if (peak &&  (error < (m_avgSample.get()*m_threshold))) {
			double endPeak = m_timer.get();
			double thisPeak = (endPeak-startPeak)/2;
			double period = thisPeak-lastPeak;
			m_avgPeriod.push(period);
			lastPeak = thisPeak;
			peak = false;
		}
		
		SmartDashboard.putDouble("Pu", round(m_avgPeriod.get()*(1000/50), 3));
		SmartDashboard.putDouble("Ku", round(Ku, 3));
	}
	
	static double round(double x, int places) {
		double scale = MathUtils.pow(10, places);
		return MathUtils.round(x*scale)/scale;
	}
}
