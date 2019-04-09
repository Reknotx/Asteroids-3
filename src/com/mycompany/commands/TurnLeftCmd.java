package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class TurnLeftCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to turn the player to the left.
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public TurnLeftCmd(GameWorld gw)
	{
		super("Turn left");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.TurnPlayer(false);
		System.out.println("Turn left");
	}
}
