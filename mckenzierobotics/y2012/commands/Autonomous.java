/**
 * Autonomous - command run during the hybrid period.
 *
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

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mckenzierobotics.lib.robot.*;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class Autonomous extends CommandBase {
	public class KeyShoot extends AutoMode {
		public String getDescription() {
			return "Key: Shoot";
		}

		public KeyShoot() {
			super();
			setShooter(shoot_key);
			sleep(3);
			shoot(.5);
			sleep(2);
			shoot(1);
		}
	}

	public class KeyShootToBridge extends KeyShoot {
		public String getDescription() {
			return "Key: Shoot -> Bridge: Pickup";
		}

		public KeyShootToBridge() {
			super();
			setRollers(true, true, true);
			goToBridgeFromKey();
			sleep(1);
			backOff();
			ramUp();
			sleep(.5);
			getBalls();
		}
	}

	public class KeyToBridge extends AutoMode {
		public String getDescription() {
			return "Key -> Bridge";
		}

		public KeyToBridge(boolean intake) {
			super();
			// Warm up, go to bridge
			setShooter(shoot_bridgesquared);
			goToBridgeFromKey();

			// Shoot
			shoot(.5);
			sleep(2);
			shoot(1);

			// End
			if (intake) {
				setRollers(true, true, true);
			}
			backOff();
		}

		public KeyToBridge() {
			this(false);
		}
	}

	public class KeyToBridgePickup extends KeyToBridge {
		public String getDescription() {
			return "Key -> Bridge: Pickup";
		}

		public KeyToBridgePickup() {
			super(true);
			ramUp();
			setShooter(shoot_bridge);
			sleep(.5);
			getBalls();
		}
	}

	public class SidekeyShoot extends AutoMode {
		public String getDescription() {
			return "Side Key: Shoot";
		}

		public SidekeyShoot() {
			super();
			setShooter(shoot_side);
			sleep(3);
			shoot(.5);
			sleep(2);
			shoot(1);
		}
	}

	public class KeyToBridgeToFoulShoot extends AutoMode {
		public String getDescription() {
			return "Key -> Bridge -> Foul: Shoot";
		}

		public KeyToBridgeToFoulShoot() {
			super();
			setShooter(shoot_foul);
			goToBridgeFromKey();
			sleep(.65);

			drive(-.6, -.6);
			sleep(3.5);
			drive(0, 0);
			ramUp();

			setRollers(true, true, true);
			sleep(.5);
			setRollers(false, false, true);
			sleep(.5);
			setRollers(true, true, true);
		}
	}

	public class FoulShoot extends AutoMode {
		public String getDescription() {
			return "Foul: Shoot";
		}

		public FoulShoot() {
			super();
			setShooter(shoot_foul);
			sleep(4);

			setRollers(true, true, false);
			sleep(.5);
			setRollers(false, false, false);
			sleep(.5);
			setRollers(true, true, false);
		}
	}

	public class FoulShootToBridge extends AutoMode {
		public String getDescription() {
			return "Foul: Shoot -> Bridge: Pickup";
		}

		public FoulShootToBridge() {
			super();
			setShooter(shoot_foul);
			sleep(3);
			shoot(1);
			sleep(2);
			shoot(1);
			setRollers(true, true, true);
			goToBridgeFromFoul();
			sleep(1);
			backOff();
			ramUp();
			sleep(.5);
			getBalls();
		}
	}
	private int mode = 0;
	private AutoMode running;
	private AutoMode[] modes = {
		new AutoMode(),
		new KeyShoot(),
		new KeyShootToBridge(),
		new KeyToBridge(),
		new KeyToBridgePickup(),
		new KeyToBridgeToFoulShoot(),
		new FoulShoot(),
		new FoulShootToBridge(),
		new SidekeyShoot(),};

	public Autonomous() {
		//SmartDashboardRadioOption selector = new SmartDashboardRadioOption(map, prog);
		//SmartDashboard.putData("Auto Mode", selector);
	}

	public void initialize() {
		//running = modes[mode];
		running = new FoulShootToBridge();
		running.start();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void interrupted() {
		running.cancel();
	}

	protected void execute() {
	}

	protected void end() {
	}
}
