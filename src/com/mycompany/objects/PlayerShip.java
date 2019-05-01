package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISteerable;
import com.mycompany.objects.Missile.MissileType;

public class PlayerShip extends Ship implements ICollider, IDrawable, ISteerable 
{
	private MissileLauncher launcher;
	private boolean collisionFlag = false;
	
	private final int MAX_MISSILE_COUNT = 10;
	
	/**
	 * The player ship that the user will control
	 */
	public PlayerShip()
	{
		super(10);
		SetSpeed(0);
		SetDirection(0);
		SetColor(0, 255, 255);
		SetSize(30);
		launcher = new MissileLauncher(this.GetDirection(), this.GetFullLocation());
	}
	
	/**
	 * 
	 * @return the direction of the launcher on ship
	 */
	public int GetLauncherDir()
	{
		return launcher.GetLauncherDir();
	}
	
	public void MoveLauncher()
	{
		launcher.SetLocation(this.GetFullLocation());
	}
	
	/**
	 * Refill player missile silo back to full
	 */
	public void Reload()
	{
		super.SetMissileCount(MAX_MISSILE_COUNT);
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
		if (increase && GetSpeed() < 750)
		{
			//Increase speed
			SetSpeed(GetSpeed() + 50);
		}
		else if (!increase && GetSpeed() > 0)
		{
			//Decrease speed
			SetSpeed(GetSpeed() - 50);
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
		
		//This is correct
		
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - 20), (xLoc + 20), xLoc };
		
		int[] yPoints = { (yLoc + 30), (yLoc - 30), (yLoc - 30), (yLoc + 30) };
		
		int nPoints = 4;
		
		g.drawPolygon(xPoints, yPoints, nPoints);
		g.fillPolygon(xPoints, yPoints, nPoints);
		
		launcher.draw(g, pCmpRelPrnt);
	}

	@Override
	public boolean collidesWith(ICollider other)
	{
		/*
		 * Testing to see if the player collides with any asteroids, enemy
		 * ships, or enemy missiles.
		 */
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
		/*
		 * If player collides with enemy entities decrease the lives here.
		 */
		if ((other instanceof Asteroid || other instanceof EnemyShip) || (other instanceof Missile && ((Missile)other).GetType() == MissileType.ENEMY))
		{
			this.setCollisionFlag();
			other.setCollisionFlag();
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
		return "Player Ship: " + parentString + launcher.toString();
	}
}
