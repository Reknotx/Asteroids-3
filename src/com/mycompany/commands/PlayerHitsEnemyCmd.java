package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.GameWorld.EntityType;

public class PlayerHitsEnemyCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command for when player collides with enemy ship
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public PlayerHitsEnemyCmd(GameWorld gw)
	{
		super("Player runs into enemy ship");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.Collision(EntityType.PLAYER, EntityType.ENEMY);
		System.out.println("Player hits enemy ship");
	}
}
