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
		
		/*Obtains the location of the object in the game world, then adds the relative origin location
		 * of mapview to it. Finally subtract half of the size so that the arc is drawn in the correct
		 * location relative to the actual location of the object.
		 * 
		 * xLoc and yLoc are NOT the actual locations of the objects themselves but are instead the
		 * locations for drawing purposes ONLY. Arcs are drawn with the x coordinate representing
		 * where the top left corner is located at and draws from there.
		 * 
		 * This is correct.
		 * 
		 */
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX() - (GetSize() / 2);
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY() - (GetSize() / 2);
		
		g.drawArc(xLoc, yLoc, this.GetSize(), this.GetSize(), 0, 360);
		g.fillArc(xLoc, yLoc, this.GetSize(), this.GetSize(), 0, 360);

	}

	//IS THIS RIGHT?! DRAW A CIRCLE TO VISUALIZE THE BOUNDING CIRCLE
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
		if (!(other instanceof SpaceStation))
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
		String thisString = " size = " + GetSize();
		return "Asteroid: " + parentString + thisString;
	}
}
