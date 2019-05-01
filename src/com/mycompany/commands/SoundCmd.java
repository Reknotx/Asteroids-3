package com.mycompany.commands;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameWorld;

public class SoundCmd extends Command 
{
	private CheckBox cb;
	private Game g;
	
	/**
	 * Creates a side menu command to toggle the sound on and off.
	 * @param gw - Reference to game world to invoke appropriate method
	 * @param cb - Reference to the check box created.
	 */
	public SoundCmd(Game g, CheckBox cb)
	{
		super("Sound ON/OFF");
		this.g = g;
		this.cb = cb;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (cb.isSelected())
		{
			System.out.println("Sound turned on");
		}
		else
		{
			System.out.println("Sound turned off");
		}
		g.ChangeSoundSetting(cb.isSelected());
	}
}
