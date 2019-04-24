package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

public class Asteroid extends MoveableGameObject implements ICollider, IDrawable
{
	private int size = 0;
	
	/**
	 * Hazard that moves through space
	 */
	public Asteroid()
	{
		SetColor(255, 255, 0);
		this.size = rng.nextInt(25) + 6;
	}
	
	/**
	 * @return The size of the asteroid
	 */
	public int GetSize()
	{
		return size;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
			
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		/*
		 * Asteroid is drawn as a circle. However with collision detection the asteroid
		 * will be the only circular object on the screen and will make differentiating
		 * between bounding rectangle and bounding circle difficult. For the moment will
		 * instead make it a square for more simplicity. Code retained for future reference.
		 *	g.drawArc(xLoc, yLoc, (this.GetSize() + 13), (this.GetSize() + 13), 0, 360);
		 *	g.fillArc(xLoc, yLoc, (this.GetSize() + 13), (this.GetSize() + 13), 0, 360);
		 * 
		 */
		
		g.drawRect(xLoc, yLoc, (this.GetSize() + 13), (this.GetSize() + 13));
		g.fillRect(xLoc, yLoc, (this.GetSize() + 13), (this.GetSize() + 13));
	}

	@Override
	public boolean collidesWith(ICollider other) 
	{
		double thisCenterX = this.GetFullLocation().getX();
		double thisCenterY = this.GetFullLocation().getY();
		
		double otherCenterX = ((GameObject)other).GetFullLocation().getX();
		double otherCenterY = ((GameObject)other).GetFullLocation().getY();
		
		
		return false;
	}

	@Override
	public void handleCollision(ICollider other) 
	{
		
	}

	public String toString()
	{
		String parentString = super.toString();
		String thisString = " size = " + size;
		return "Asteroid: " + parentString + thisString;
	}
}
