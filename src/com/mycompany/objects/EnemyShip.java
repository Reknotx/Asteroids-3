package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;

public class EnemyShip extends Ship implements ICollider, IDrawable
{
	private int size;
	private MissileLauncher launcher;
	
	/**
	 * Creates an enemy ship object in the world. Enemy ships have two sizes, small and large.
	 * Enemies also have missiles that they can fire, a total of two.
	 */
	public EnemyShip()
	{
		super(2);
		launcher = new MissileLauncher(super.GetDirection());
		size = (rng.nextInt(2) + 1) * 10;
		SetColor(255, 0, 0);
	}
	
	/**
	 * @return integer value representing the size (10 = small / 20 = large)
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * 
	 * @return The direction of the enemy launcher, same as ship direction
	 */
	public int GetLauncherDir()
	{
		return launcher.GetLauncherDir();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) 
	{
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - size), (xLoc + size), xLoc };
		
		int[] yPoints = { (yLoc + size), (yLoc - size), (yLoc - size), (yLoc + size) };
		
		int nPoints = 4;
		
		g.drawPolygon(xPoints, yPoints, nPoints);
		g.fillPolygon(xPoints, yPoints, nPoints);
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
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = " size = " + size;
		return "Non-Player Ship: " + parentString + thisString;
	}
}
