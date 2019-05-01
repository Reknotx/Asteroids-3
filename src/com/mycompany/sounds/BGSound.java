package com.mycompany.sounds;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable
{
	private Media m;
	
	/**
	 * Class used to play the looping background music. Music immediately starts over
	 * after finishing the first time.
	 * @param fileName - name of the music to play and loop
	 */
	public BGSound(String fileName)
	{
		try
		{
			InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
			
			m = MediaManager.createMedia(in, "audio/wav", this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void pause() { m.pause(); }
	public void play() { m.play(); }

	@Override
	public void run()
	{
		m.setTime(0);
		m.play();
	}

}
