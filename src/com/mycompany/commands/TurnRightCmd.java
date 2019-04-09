package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class TurnRightCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to turn the player to the right.
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public TurnRightCmd(GameWorld gw)
	{
		super("Turn right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.TurnPlayer(true);
		System.out.println("Turn right");
	}
}
