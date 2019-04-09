package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class TickCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to advance game time
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public TickCmd(GameWorld gw)
	{
		super("Advance game clock");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.AdvanceGameClock();
		System.out.println("Advance game clock");
	}
}
