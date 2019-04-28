package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

public class SpaceStation extends FixedGameObject implements ICollider, IDrawable
{
	private final int SPACE_STATION_WIDTH = 46;
	private final int SPACE_STATION_HEIGHT = 30;
	private final int TIME_TILL_NEXT_RELOAD = 5;
	
	private int thisId;
	private int blinkRate;
	private int timeSinceBlink;
	private int reloadDelay = 0;
	
	private boolean lightsOn;
	private boolean collisionFlag = false;
	private boolean reloadAvailable = true;
	
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
	 * 
	 * Also has the side effect of increasing the counter to the next available reload for this space station
	 * if the space station had recently reloaded the player ship. Helps to avoid repeated spamming of reload 
	 * on player.
	 */
	public void IncreaseBlinkTime()
	{
		timeSinceBlink++;
		if (timeSinceBlink == blinkRate)
		{
			lightsOn = !lightsOn;
			timeSinceBlink = 0;
		}

		if (!reloadAvailable)
		{
			IncreaseReloadDelayTime();
		}
	}
	
	private void IncreaseReloadDelayTime()
	{
		reloadDelay++;
		if (reloadDelay == TIME_TILL_NEXT_RELOAD)
		{
			reloadAvailable = true;
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
		
		if (lightsOn && reloadAvailable)
		{			
			g.fillArc(xLoc, yLoc, SPACE_STATION_WIDTH, SPACE_STATION_HEIGHT, 0, 360);
		}
	}

	@Override
	public boolean collidesWith(ICollider other)
	{
		boolean result = false;
		
		double thisCenterX = this.GetFullLocation().getX();
		double thisCenterY = this.GetFullLocation().getY();
		
		double otherCenterX = ((GameObject)other).GetFullLocation().getX();
		double otherCenterY = ((GameObject)other).GetFullLocation().getY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		// find square of sum of radii
		int thisRadius= this.GetSize() / 2;
		int otherRadius= ((GameObject)other).GetSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;		
	}

	@Override
	public void handleCollision(ICollider other)
	{
		if (other instanceof PlayerShip && reloadAvailable)
		{
			((PlayerShip) other).Reload();
			reloadAvailable = false;
			reloadDelay = 0;
		}
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
