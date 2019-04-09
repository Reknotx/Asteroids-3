package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddAsteroidCmd extends Command
{
	private GameWorld gw;
	
	/**
	 * 
	 * @param gw
	 */
	public AddAsteroidCmd(GameWorld gw)
	{
		super("Add asteroid");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.SpawnAsteroid();
		System.out.println("Add asteroid");
	}
}
