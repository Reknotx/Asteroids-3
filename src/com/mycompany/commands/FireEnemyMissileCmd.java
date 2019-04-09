package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class FireEnemyMissileCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to fire enemy missile
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public FireEnemyMissileCmd(GameWorld gw)
	{
		super("Fire enemy missile");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.FireEnemymissile();
		System.out.println("Fire enemy missile");
	}
}
