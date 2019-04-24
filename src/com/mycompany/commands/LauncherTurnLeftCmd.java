package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class LauncherTurnLeftCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to rotate missile launcher left
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public LauncherTurnLeftCmd(GameWorld gw)
	{
		super("Rotate launcher left");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.RotateLauncher(-10);
		System.out.println("Rotate launcher left");
	}
}
