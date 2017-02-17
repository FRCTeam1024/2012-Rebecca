/**
 * Copyright (c) 2011 Luke Shumaker
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

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * TODO: Write JavaDocs
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class RollingAvg implements PIDSource, PIDOutput {
	private PIDSource source = null;
	private double[] points;
	private double avg;
	private int i;

	public RollingAvg(int len) {
		points = new double[len];
		i = 0;
		avg = 0;
	}
	public RollingAvg(int len, PIDSource src) {
		this(len);
		source = src;
	}

	public double push(double v) {
		avg -= points[i];
		points[i] = v/points.length;
		avg += points[i];
		i++; i %= points.length;
		return avg;
	}

	public double get() {
		return avg;
	}

	public double pidGet() {
		if (source!=null) return push(source.pidGet());
		return get();
	}
	
	public void pidWrite(double output) {
		push(output);
	}
}
