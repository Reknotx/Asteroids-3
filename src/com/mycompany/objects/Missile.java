package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISelectable;

public class Missile extends MoveableGameObject implements ICollider, IDrawable, ISelectable
{
	private final int MISSILE_FUEL = 100;
	private final int MISSILE_SIZE = 25;
	
	public enum MissileType { PLAYER, ENEMY }
	private MissileType type;
	private GameObject shotObject = null;

	private boolean collisionFlag = false;
	private boolean selected = false;

	private int fuelLevel;
	private int scoreGained = 0;
	private int timeElapsed = 0;
	
	/**
	 * Missile that travels through world.
	 * @param missileLauncherDir - The direction to fire the missile
	 * @param speed - The speed of the ship currently firing the missile, missiles always need to be faster than the firing ship
	 * @param loc - the location of the ship firing the missile
	 * @param type - The type of ship firing the missile (Player or Enemy)
	 */
	public Missile(int missileLauncherDir, int speed, Point2D loc, MissileType type)
	{
		//Currently spawns the missile on the player's location, needs to change to be fired
		//closer to the end of the missile launcher, will deal with at later time.
		
		fuelLevel = MISSILE_FUEL;
		SetLocation(loc);
		SetSpeed(speed);
		SetDirection(missileLauncherDir);
		SetSize(MISSILE_SIZE);
		
		this.type = type;
		switch (type)
		{
			case PLAYER:
				SetColor(0, 255, 255);
				break;
				
			case ENEMY:
				SetColor(255, 0, 0);
				break;
		}
	}
	
	/**
	 * @return The current fuel level associated with this missile
	 */
	public int GetFuel()
	{
		return fuelLevel;
	}
	
	public void ResetFuel()
	{
		fuelLevel = MISSILE_FUEL;
	}
	
	/**
	 * @return The type of missile that was fired, either Player or Enemy
	 */
	public MissileType GetType()
	{
		return type;
	}
	
	public int GetScoreGained()
	{
		return scoreGained;
	}

	public void DecreaseFuel() 
	{
		fuelLevel--;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX() - (MISSILE_SIZE / 2);
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY() - (MISSILE_SIZE / 2);
		
		g.drawRect(xLoc, yLoc, MISSILE_SIZE, MISSILE_SIZE);
		g.fillRect(xLoc, yLoc, MISSILE_SIZE, MISSILE_SIZE);
		
		if (isSelected())
		{
			g.drawRect(xLoc - 5, yLoc - 5, this.GetSize() + 10, this.GetSize() + 10);
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
		if (this.GetType() == MissileType.PLAYER)
		{
			if (other instanceof Asteroid)
			{
				this.setCollisionFlag();
				other.setCollisionFlag();
				scoreGained = 10;
			}
			else if (other instanceof EnemyShip)
			{
				this.setCollisionFlag();
				other.setCollisionFlag();
				scoreGained = 20;
			}
		}
		else if (this.GetType() == MissileType.ENEMY)
		{
			if (other instanceof PlayerShip)
			{
				this.setCollisionFlag();
				other.setCollisionFlag();
			}
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

	@Override
	public void setSelected(boolean yes)
	{
		selected = yes;
	}

	@Override
	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt)
	{
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		if ( ((px >= xLoc - GetSize() /  2) && (px <= xLoc + GetSize() / 2)) && 
				((py >= yLoc - GetSize() / 2) && (py <= yLoc + GetSize() / 2)))
		{
			return true;
		}
		else
		{
			return false;			
		}
	}
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = " fuel = " + fuelLevel;
		if (type == MissileType.PLAYER)
		{
			return "PS's Missile: " + parentString + thisString;			
		}
		else
		{
			return "NPS's Missile: " + parentString + thisString;
		}
	}
}
