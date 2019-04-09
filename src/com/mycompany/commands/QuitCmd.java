package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class QuitCmd extends Command 
{
	/**
	 * Creates a side menu command to quit game, prompts player if they want to quit first
	 */
	public QuitCmd()
	{
		super("Quit");
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (Dialog.show("Quit", "Are you sure you want to quit?", "Yes", "No"))
		{
			System.exit(0);
		}
	}
}
