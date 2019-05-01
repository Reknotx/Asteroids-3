package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class LauncherTurnRightCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to rotate missile launcher right
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public LauncherTurnRightCmd(GameWorld gw)
	{
		super("Rotate launcher right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.RotateLauncher(15);
		System.out.println("Rotate launcher right");
	}
}
