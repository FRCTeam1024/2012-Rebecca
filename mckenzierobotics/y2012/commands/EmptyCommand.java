package org.mckenzierobotics.y2012.commands;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class EmptyCommand extends CommandBase {
	public EmptyCommand() { super(); }
	protected void initialize() {}
	protected void execute() {}
	protected boolean isFinished() { return true; }
	protected void end() {}
	protected void interrupted() {}
}
