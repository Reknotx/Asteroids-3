package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class RefuelCmd extends Command
{
	private GameWorld gw;
	
	
	/**
	 * Used to refuel a selected missile so long as it is selected
	 * @param gw - reference to the gameworld class
	 */
	public RefuelCmd(GameWorld gw)
	{
		super("Refuel");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.RefuelMissile();
	}
}
