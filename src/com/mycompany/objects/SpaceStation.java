package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

public class SpaceStation extends FixedGameObject implements ICollider, IDrawable
{
	private final int SPACE_STATION_WIDTH = 46;
	private final int SPACE_STATION_HEIGHT = 30;
	
	private int thisId;
	private int blinkRate;
	private int timeSinceBlink;
	private boolean lightsOn;
	private boolean collisionFlag = false;
	
	/**
	 * Creates a fixed Space Station object that doesn't move
	 */
	public SpaceStation()
	{
		thisId = GetId();
		blinkRate = rng.nextInt(4) + 1;
		lightsOn = true;
		timeSinceBlink = 0;
		SetColor(255, 0, 255);
		SetSize(50);
	}
	
	/**
	 * @return Blink rate associated with space station
	 */
	public int GetRate()
	{
		return blinkRate;
	}
	
	/**
	 * @return The ID associated to the space station
	 */
	public int GetId()
	{
		return thisId;
	}
	
	/**
	 * Increases the counter since the last blink of the lights, turning on and off, resets to zero when toggle
	 */
	public void IncreaseBlinkTime()
	{
		timeSinceBlink++;
		if (timeSinceBlink == blinkRate)
		{
			lightsOn = !lightsOn;
			timeSinceBlink = 0;
		}
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		//This is correct
		
		g.setColor(this.GetColor());

		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX() - (SPACE_STATION_WIDTH / 2);
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY() - (SPACE_STATION_HEIGHT / 2);
		
		g.drawArc(xLoc, yLoc, SPACE_STATION_WIDTH, SPACE_STATION_HEIGHT, 0, 360);
		
		if (lightsOn)
		{			
			g.fillArc(xLoc, yLoc, SPACE_STATION_WIDTH, SPACE_STATION_HEIGHT, 0, 360);
		}
	}

	@Override
	public boolean collidesWith(ICollider other)
	{
		return false;
	}

	@Override
	public void handleCollision(ICollider other)
	{
		
	}

	@Override
	public void setCollisionFlag()
	{
		collisionFlag = true;
	}

	@Override
	public boolean getCollisionFlag()
	{
		return collisionFlag;
	}
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = " rate = " + blinkRate;
		return "Station: " + parentString + thisString;
	}
}
