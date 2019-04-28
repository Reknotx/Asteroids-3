package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;

public class PauseCmd extends Command
{
	Game g;
	
	public PauseCmd(Game g)
	{
		super("Pause");
		this.g = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		g.PauseAndUnPauseGame();
	}
}