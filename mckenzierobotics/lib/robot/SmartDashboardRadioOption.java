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
package org.mckenzierobotics.lib.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData;

/**
 *
 * @author Luke Shumaker <lukeshu@sbcglobal.net>
 */
public class SmartDashboardRadioOption implements SmartDashboardData {
	private final String[] options;
	private final String def;
	private String selected;
	private NetworkTable table;

	public SmartDashboardRadioOption(String[] options, String def) {
		this.options = options;
		this.def = def;
		selected = def;
	}

	public SmartDashboardRadioOption(String[] options, int def) {
		this(options, options[def]);
	}

	public String getSelectedString() {
		try {
			selected = getTable().getString("selected");
		} catch (Exception e) {
		}
		return selected;
	}

	public int getSelectedIndex() {
		getSelectedString();
		for (int i = 0; i < options.length; i++) {
			if (options[i].equals(selected)) {
				return i;
			}
		}
		return -1;
	}

	public String getType() {
		return "String Chooser";
	}

	public NetworkTable getTable() {
		if (table == null) {
			table = new NetworkTable();
			table.putInt("count", options.length);
			table.putString("default", def);
			table.putString("selected", selected);
			for (int i = 0; i < options.length; i++) {
				table.putString(String.valueOf(i), options[i]);
			}
		}
		return table;
	}
}

