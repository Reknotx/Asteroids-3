package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddSpaceStationCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to spawn a space station
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public AddSpaceStationCmd(GameWorld gw)
	{
		super("Add space station");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.SpawnStation();
		System.out.println("Add space station");
	}
}
