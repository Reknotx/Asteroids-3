package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class ReloadCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command for reloading player missile silo
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public ReloadCmd(GameWorld gw)
	{
		super("Reload missiles");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.ReloadMissiles();
		System.out.println("Reload missiles");
	}
}
