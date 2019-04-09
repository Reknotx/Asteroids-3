package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.GameWorld.EntityType;

public class AsteroidHitsAsteroidCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to remove two asteroids that collide
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public AsteroidHitsAsteroidCmd(GameWorld gw)
	{
		super("Asteroid hits another asteroid");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.Collision(EntityType.ASTEROID, EntityType.ASTEROID);
		System.out.println("Asteroid hits another asteroid");
	}
}
