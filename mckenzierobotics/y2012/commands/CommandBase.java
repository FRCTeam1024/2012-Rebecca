/**
 * CommandBase - defines robot hardware; superclass of all commands.
 * 
 * Copyright (c) FIRST 2008. All Rights Reserved.
 * Open Source Software - may be modified and shared by FRC teams. The code
 * must be accompanied by the FIRST BSD license file in the root directory of
 * the project.
 * 
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
package org.mckenzierobotics.y2012.commands;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mckenzierobotics.lib.robot.*;
import org.mckenzierobotics.y2012.OI;
import org.mckenzierobotics.y2012.lib.*;
import org.mckenzierobotics.y2012.subsystems.*;

/**
 * The base for all commands, also the class defining robot hardware/config.
 * All atomic commands should subclass CommandBase. CommandBase stores creates
 * and stores each control system.
 * 
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public abstract class CommandBase extends org.mckenzierobotics.lib.robot.CommandBase {
	/** Subsystem: LCD. */
	public static final LCD lcd = new LCD();
	
	/** Subsystem: TankChassis. */
	public static final TankChassis chassis = getDrive();

	/** Subsystem: Compressor.
	 * Switch (DIO), Relay (relay)
	 */
	public static Compressor compressor = new Compressor(1, 8);
	
	/** Subsystem: CoopertitionRam. */
	public static CoopertitionRam coopertitionram =
			new CoopertitionRam(new Solenoid(1));
	/** Subsystem: Shifter. */
	public static Shifter shifter = new Shifter(new Solenoid(3));
	
	public static final HiTechnicAccel accel = new HiTechnicAccel();
	
	public static final double shooterMaxRPS = 55;//75
	public static final RollingAvg shooterTe = getShootSource(2, 3);
	public static final RollingAvg shooterBe = getShootSource(4, 5);
	public static final PIDOutput shooterTm = getJaguar(6);
	public static final PIDOutput shooterBm = getJaguar(7);
	public static final FeedForward shooterT = getShootController(
			new SendingPIDOutput("top-jag", shooterTm),
			new SendingPIDSource("top"    , shooterTe));
	public static final FeedForward shooterB = getShootController(
			new SendingPIDOutput("bot-jag", shooterBm),
			new SendingPIDSource("bottom" , shooterBe));
	/** Subsystem: Shooter. */
	public static final Shooter shooter =
			new Shooter(shooterT, shooterB, shooterMaxRPS);
	
	/**
	 * TODO: Subsystem: turret
	 * 4: Turret
	 */
	
	public static final FeedRoller rollerFeed = new FeedRoller(getJaguar(5) , .75);
	public static final FeedRoller rollerMain = new FeedRoller(getJaguar(10), .75);
	public static final FeedRoller rollerGrab = new FeedRoller(getJaguar(11), .75);
	
	public static final AxisCamera camera = null;//AxisCamera.getInstance();
	public static PIDServo cameraServoX = new PIDServo(1, false);
	public static PIDServo cameraServoY = new PIDServo(2, true);
	/** Subsystem: TrackerSource. */
	public static final TrackerSource cameraTracker = new TrackerSource(camera);
	
	/**
	 * Handle all the nasty bits of initializing the TankChassis (flipping
	 * motors and such.
	 * 
	 * @return The TankChassis
	 */
	public static TankChassis getDrive() {
		CANJaguar[] lDrive = new CANJaguar[2];
		lDrive[0] = getJaguar(2);
		lDrive[1] = getJaguar(3);
		CANJaguar[] rDrive = new CANJaguar[2];
		rDrive[0] = getJaguar(8);
		rDrive[1] = getJaguar(9);
		
		double[] lDriveScalar = {-1, -1};
		double[] rDriveScalar = {1, 1};
		
		return new TankChassis(new PIDOutputSplitter(lDrive, lDriveScalar),
		                       new PIDOutputSplitter(rDrive, rDriveScalar));
	}
	
	/**
	 * Instantiate and configure a shooter encoder.
	 * 
	 * @param a DIO source a
	 * @param b DIO source b
	 * @return A stabilized PIDSource of the encoder rate.
	 */
	public static RollingAvg getShootSource(int a, int b) {
		Encoder e = new Encoder(a, b, true);
		e.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		e.setDistancePerPulse(1.0/250.0);// 1 rotation = 250 pulses
		//e.setDistancePerPulse(1);
		e.start();
		return new RollingAvg(5, e);
	}

	/**
	 * Return a PIDController for a shooter
	 * @param id The CANJaguar ID to use for PIDOutput
	 * @param e The encoder PIDSource
	 * @return The configured and tuned PIDController
	 */
	public static FeedForward getShootController(PIDOutput m, PIDSource e) {
		//FeedForward ff = new FeedForward(0.02, 0.001, 0.005, e, m, true){
		FeedForward ff = new FeedForward(0.001, 0.0001, 0, e, m, true){
			public double calculate(double setpoint) {
				return (.8/60)*setpoint*.85;
			}
		};
		PIDController pid = ff.getPID();
		pid.setInputRange(-shooterMaxRPS, shooterMaxRPS);
		pid.setOutputRange(-1, 1);
		pid.enable();
		return ff;
	}
	
	/**
	 * Set up the initial SmartDashboard data.
	 */
	public static void initDashboard() {
		// Show what command your subsystem is running on the SmartDashboard
		//SmartDashboard.putData(exampleSubsystem);
		//SmartDashboard.putData("shooterT", shooterT);
		//SmartDashboard.putData("shooterB", shooterB);
	}
	
	// You don't really need to edit below here. ///////////////////////////////
	
	/** The operator interface. */
	public static OI oi;
	/**
	 * Instantiate the operator interface.
	 * @see #oi
	 */
	public static void initOI() {
		oi = new OI();
	}
	
	public static void init() {
		//initRobot();
		initOI();
		initDashboard();
	}
	
	public CommandBase() { super(); }
	public CommandBase(double timeout) { super(timeout); }
	public CommandBase(String name) { super(name); }
	public CommandBase(String name, double timeout) { super(name, timeout); }
}
