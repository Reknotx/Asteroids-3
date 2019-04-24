package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISteerable;

public class PlayerShip extends Ship implements ICollider, IDrawable, ISteerable 
{
	private MissileLauncher launcher;
	
	/**
	 * The player ship that the user will control
	 */
	public PlayerShip()
	{
		super(10);
		launcher = new MissileLauncher(0);
		SetSpeed(0);
		SetDirection(0);
		SetColor(0, 255, 255);
	}
	
	/**
	 * 
	 * @return the direction of the launcher on ship
	 */
	public int GetLauncherDir()
	{
		return launcher.GetLauncherDir();
	}
	
	/**
	 * Refill player missile silo back to full
	 */
	public void Reload()
	{
		super.SetMissileCount(10);
	}
	
	/**
	 * Resets the player's position in the gameworld back to spawn.
	 */
	public void ResetPosition(double x, double y)
	{
		SetLocation(x, y);
	}
	
	/**
	 * 
	 * @param increase - if true increase the speed of ship, decrease if false
	 */
	public void AdjustSpeed(boolean increase)
	{
		if (increase && GetSpeed() < 15)
		{
			//Increase speed
			SetSpeed(GetSpeed() + 1);
		}
		else if (!increase && GetSpeed() > 0)
		{
			//Decrease speed
			SetSpeed(GetSpeed() - 1);
		}
	}
	
	@Override
	public void Steer(int amount) 
	{
		if (amount < 0 && GetDirection() + amount < 0)
		{
			/*Amount, which is a negative number, is added to current direction
			 * which is the same as subtracting from direction. We will have a negative
			 * number. Then add 360 to that number to get it's proper position.
			 */
			SetDirection(GetDirection() + amount + 360);
		}
		else
		{
			if (GetDirection() + amount >= 360)
			{
				SetDirection(GetDirection() + amount - 360);
			}
			else
			{
				SetDirection(GetDirection() + amount);			
			}
		}
	}
	
	/**
	 * @param amount - value to change the launcher direction by
	 */
	public void ChangeLauncherDir(int amount)
	{
		launcher.Steer(amount);
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - 15), (xLoc + 15), xLoc };
		
		int[] yPoints = { (yLoc + 15), (yLoc - 15), (yLoc - 15), (yLoc + 15) };
		
		int nPoints = 4;
		
		g.drawPolygon(xPoints, yPoints, nPoints);
		g.fillPolygon(xPoints, yPoints, nPoints);
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
	
	public String toString()
	{
		String parentString = super.toString();
		return "Player Ship: " + parentString + launcher.toString();
	}
}
