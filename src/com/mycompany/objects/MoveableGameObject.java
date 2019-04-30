package com.mycompany.objects;

import com.codename1.ui.geom.Point2D;
import com.mycompany.interfaces.IMoveable;

public abstract class MoveableGameObject extends GameObject implements IMoveable
{
	private int speed;
	private int direction;
	
	/**
	 * Indicates that an object is moveable. Randomizes the speed and direction of the object
	 */
	public MoveableGameObject()
	{
		speed = (rng.nextInt(3) + 3) * 50;
		direction = rng.nextInt(360);
	}
	
	/**
	 * @return The speed associated with this object
	 */
	public int GetSpeed()
	{
		return speed;
	}
	
	/**
	 * @param s - set the speed of this object to s
	 */
	public void SetSpeed(int s)
	{
		speed = s;
	}
	
	/**
	 * @return the integer value indicating the direction.
	 */
	public int GetDirection()
	{
		return direction;
	}
	
	/**
	 * @param d - set direction to value d
	 */
	public void SetDirection(int d)
	{
		direction = d;
	}
	
	@Override
	public void Move(double mapWidth, double mapHeight, double time)
	{
		Point2D newLoc = new Point2D(0.0, 0.0);
		Point2D oldLoc = GetFullLocation();
		double deltaX = 0.0;
		double deltaY = 0.0;
		
		//Used to calculate new location and to determine if teleportation is necessary.
		double newX = 0.0;
		double newY = 0.0;
		
		//If going directly north or south only affect Y
		if (direction == 0 || direction == 180)
		{
			deltaY = Math.sin( Math.toRadians(90 - direction) ) * (speed * (time / 1000));
		}
		else if (direction == 90 || direction == 270)
		{
			deltaX = Math.cos( Math.toRadians(90 - direction) ) * (speed * (time / 1000));
		}
		else
		{
			deltaX = Math.cos( Math.toRadians(90 - direction) ) * (speed * (time / 1000));
			deltaY = Math.sin( Math.toRadians(90 - direction) ) * (speed * (time / 1000));
		}
		
		newX = deltaX + oldLoc.getX();
		newY = deltaY + oldLoc.getY();
		
		/*
		 * The following functions are intended to better simulate the actual arcade game by Atari.
		 * When reaching the edges of the game world in the original game you are immediately
		 * teleported to the opposite wall with the same x or y values to be expected depending on 
		 * the wall hit. So if you hit the right wall you are teleported to the left wall, if you hit
		 * the top wall you are teleported to the bottom wall, and vice versa.
		 * 
		 * The absolute math functions are to ensure that you don't subtract with a negative
		 * value which will instead be adding a positive value and then being trapped in
		 * an infinite teleportation loop for the remainder of the game.
		 */
		
		if (newX >= mapWidth)
		{
			newX = newX - mapWidth;
		}
		else if (newX <= 0.0)
		{
			newX = mapWidth - Math.abs(newX);
		}
		
		if (newY >= mapHeight)
		{
			newY = newY - mapHeight;
		}
		else if (newY <= 0.0)
		{
			newY = mapHeight - Math.abs(newY);
		}
		
		newLoc.setX(newX);
		newLoc.setY(newY);
		
		SetLocation(newLoc);
	}

	public String toString()
	{
		String parentString = super.toString();
		String thisString = "speed = " + speed + " direction = " + direction + " ";
		return parentString + thisString;
	}
}