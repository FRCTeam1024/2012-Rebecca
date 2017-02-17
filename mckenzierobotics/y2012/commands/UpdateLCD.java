package org.mckenzierobotics.y2012.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class UpdateLCD extends CommandBase {
	
	public UpdateLCD() {
		super();
		requires(lcd);
		setRunWhenDisabled(true);
	}

	protected void execute() {
		/*
		lcd.lines[0] = "lJoy: "+oi.lJoystick.getY();
		lcd.lines[1] = "rJoy: "+oi.rJoystick.getY();
		lcd.lines[2] = "switch: "+compressor.getPressureSwitchValue();
		lcd.lines[3] = "comp: "+compressor.enabled();
		/**/
		
		SmartDashboard.putDouble("Backspin (%)", shooter.getDifference()*100);
		
		lcd.lines[0] = "backspin (%): "+MathUtils.round(shooter.getDifference()*100);
		
		lcd.lines[2] = "t (RPS): "+
				MathUtils.round(shooterTe.get())
				+" / "+
				MathUtils.round(shooterT.getPID().getSetpoint());
		lcd.lines[3] = "b (RPS): "+
				MathUtils.round(shooterBe.get())
				+" / "+
				MathUtils.round(shooterB.getPID().getSetpoint());
		lcd.lines[4] = "compressor: "+(compressor.getPressureSwitchValue()?"off":"on");
		lcd.lines[5] = "shifter: "+(shifter.getHigh()?"high":"low");
		/**/
		/*
		lcd.lines[0] = "servoX: "+cameraServoX.getAngle();
		lcd.lines[1] = "servoY: "+cameraServoY.getAngle();
		
		lcd.lines[3] = "camX: "+cameraTracker.getX();
		lcd.lines[4] = "camY: "+cameraTracker.getY();
		lcd.lines[5] = "targets: "+cameraTracker.getNumTargets();
		/**/ 
		/*
		lcd.lines[0] = "X: "+accel.getAcceleration(ADXL345_I2C.Axes.kX);
		lcd.lines[1] = "Y: "+accel.getAcceleration(ADXL345_I2C.Axes.kY);
		lcd.lines[2] = "Z: "+accel.getAcceleration(ADXL345_I2C.Axes.kZ);
		/**/ 
		
		lcd.update();
	}
	
	protected boolean isFinished() {
		return false;
	}

	protected void initialize() {}
	protected void end() {}
	protected void interrupted() {}
}
