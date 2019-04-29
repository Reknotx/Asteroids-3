package com.mycompany.objects;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.ISelectable;

public class EnemyShip extends Ship implements ICollider, IDrawable, ISelectable
{
	//private int size;
	private MissileLauncher launcher;
	
	private boolean collisionFlag = false;
	private boolean selected = false;
	
	/**
	 * Creates an enemy ship object in the world. Enemy ships have two sizes, small and large.
	 * Enemies also have missiles that they can fire, a total of two.
	 */
	public EnemyShip()
	{
		super(2);
		launcher = new MissileLauncher(super.GetDirection());
		SetSize((rng.nextInt(2) + 1) * 10);
		SetColor(255, 0, 0);
	}
	
//	/**
//	 * @return integer value representing the size (10 = small / 20 = large)
//	 */
//	public int GetSize()
//	{
//		return size;
//	}
	
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
		
		//This is correct
		
		g.setColor(this.GetColor());
		
		int xLoc = (int)this.GetFullLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.GetFullLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - GetSize()), (xLoc + GetSize()), xLoc };
		
		int[] yPoints = { (yLoc + GetSize()), (yLoc - GetSize()), (yLoc - GetSize()), (yLoc + GetSize()) };
		
		int nPoints = 4;
		
		g.drawPolygon(xPoints, yPoints, nPoints);
		g.fillPolygon(xPoints, yPoints, nPoints);
		
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
	public void handleCollision(ICollider other) {
		// TODO Auto-generated method stub
		
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
		String thisString = " size = " + GetSize();
		return "Non-Player Ship: " + parentString + thisString;
	}
}
