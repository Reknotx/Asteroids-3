package com.mycompany.sounds;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable
{
	private Media m;
	
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
