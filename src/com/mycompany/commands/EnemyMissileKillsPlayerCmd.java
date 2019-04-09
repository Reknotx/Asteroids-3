package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class EnemyMissileKillsPlayerCmd extends Command 
{
	private GameWorld gw;
	
	/**
	 * Creates a button command to kill player with enemy missile
	 * @param gw - Reference to game world to invoke appropriate method
	 */
	public EnemyMissileKillsPlayerCmd(GameWorld gw)
	{
		super("Enemy missile hits player");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.KillPlayerWithEnemyMissile();
		System.out.println("Enemy missile hits player");
	}
}
