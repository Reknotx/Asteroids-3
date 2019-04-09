package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddEnemyShipCmd extends Command 
{
	private GameWorld gw;
		
	/**
	 * Creates a button command to spawn an enemy ship
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public AddEnemyShipCmd(GameWorld gw)
	{
		super("Add enemy ship");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.SpawnEnemy();
		System.out.println("Add enemy ship");
	}
}
