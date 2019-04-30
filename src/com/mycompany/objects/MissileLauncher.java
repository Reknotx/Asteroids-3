package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISteerable;

public class MissileLauncher extends MoveableGameObject implements IDrawable, ISteerable
{	
	/**
	 * @param startDir - The starting direction of the launcher, will typically be zero unless stated otherwise
	 */
	public MissileLauncher(int startDir, Point2D startLoc)
	{
		SetColor(255, 255, 255);
		SetDirection(startDir);
		SetLocation(startLoc);
	}
	
	/**
	 * @return The current direction of the launcher
	 */
	public int GetLauncherDir()
	{
		return GetDirection();
	}
	
	/**
	 * @param amount - Value to set the launcher direction too
	 */
	public void SetLauncherDir(int amount)
	{
		SetDirection(amount);
	}
	
	public void SetLauncherLoc(Point2D loc)
	{
		SetLocation(loc);
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
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		double angle = Math.toRadians(90 - this.GetLauncherDir());
		
		double deltaX = Math.cos(angle);
		double deltaY = Math.sin(angle);
		
		g.drawLine(xLoc, yLoc, (int)(xLoc + (50 * deltaX)), (int)(yLoc + (50 * deltaY)));
		
	}

	public String toString()
	{
		return " Missile Launcher Direction = " + GetDirection();
	}
}
