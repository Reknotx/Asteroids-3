package com.mycompany.sounds;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound
{
	private Media m;
	
	/**
	 * Class used to play single use sounds that do not repeat.
	 * @param fileName - the name of the sound file to be played
	 */
	public Sound(String fileName)
	{
		try
		{
			InputStream in = Display.getInstance().getResourceAsStream(getClass(),"/"+fileName);
			
			m = MediaManager.createMedia(in, "audio/wav");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		m.setTime(0);
		m.play();
	}
}