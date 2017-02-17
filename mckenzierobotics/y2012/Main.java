package org.mckenzierobotics.y2012;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mckenzierobotics.y2012.commands.CommandBase;
import org.mckenzierobotics.lib.robot.CommandRobot;
import org.mckenzierobotics.y2012.commands.*;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class Main extends CommandRobot {
	public Command getTeleop() { return new Teleop(); }
	public Command getAutonomous() { return new Autonomous(); }
}
