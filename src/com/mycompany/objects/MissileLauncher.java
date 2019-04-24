package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISteerable;

public class MissileLauncher extends MoveableGameObject implements IDrawable, ISteerable
{	
	/**
	 * @param startDir - The starting direction of the launcher, will typically be zero unless stated otherwise
	 */
	public MissileLauncher(int startDir)
	{
		SetColor(0, 0, 0);
		SetDirection(startDir);
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
		
		int x2 = (int) Math.cos( Math.toRadians(90 - GetDirection())) + xLoc + 15;
		int y2 = (int) Math.sin( Math.toRadians(90 - GetDirection())) + yLoc + 15;
		
		g.drawLine(xLoc, yLoc, x2, y2);
		
	}

	public String toString()
	{
		return " Missile Launcher Direction = " + GetDirection();
	}
}
