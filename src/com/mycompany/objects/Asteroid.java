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
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = " size = " + size;
		return "Asteroid: " + parentString + thisString;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collidesWith(ICollider other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider other) {
		// TODO Auto-generated method stub
		
	}
}
