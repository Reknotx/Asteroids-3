package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

public class Asteroid extends MoveableGameObject implements ICollider, IDrawable
{
	//private int size = 0;
	private boolean collisionFlag = false;
	
	/**
	 * Hazard that moves through space
	 */
	public Asteroid()
	{
		SetColor(255, 255, 0);
		/*
		 * Added more to the size of the asteroid so it appears nicely on screen.
		 * Also so that it is easier to find the bounds so i don't have to make
		 * janky stuff.
		 */
		SetSize(rng.nextInt(25) + 20);
	}
	
//	/**
//	 * @return The size of the asteroid
//	 */
//	public int GetSize()
//	{
//		return size;
//	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
			
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		g.drawArc(xLoc, yLoc, this.GetSize(), this.GetSize(), 0, 360);
		g.fillArc(xLoc, yLoc, this.GetSize(), this.GetSize(), 0, 360);

	}

	@Override
	public boolean collidesWith(ICollider other)
	{
		boolean result = false;
		double thisCenterX = this.GetFullLocation().getX() + (this.GetSize() / 2);
		double thisCenterY = this.GetFullLocation().getY() + (this.GetSize() / 2);
		
		double otherCenterX = ((GameObject)other).GetFullLocation().getX() + (((GameObject)other).GetSize() / 2);
		double otherCenterY = ((GameObject)other).GetFullLocation().getY() + (((GameObject)other).GetSize() / 2);
		
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
		this.setCollisionFlag();
		other.setCollisionFlag();
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
		String thisString = " size = " + GetSize();
		return "Asteroid: " + parentString + thisString;
	}
}
