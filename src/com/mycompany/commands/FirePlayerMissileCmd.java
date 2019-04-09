package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class FirePlayerMissileCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to fire player missile
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public FirePlayerMissileCmd(GameWorld gw)
	{
		super("Fire player missile");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.FirePlayerMissile();
		System.out.println("Fire player missile");
	}
}
