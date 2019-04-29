package com.mycompany.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.IGameWorld;
import com.mycompany.interfaces.IIterator;
import com.mycompany.interfaces.ISelectable;
import com.mycompany.objects.GameObject;

//View in MVC architecture
public class MapView extends Container implements Observer 
{	
	private IGameWorld gwProxy;
	
	private int px;
	private int py;
	
	/**
	 * Creates a container that will display the game objects later in graphical form.
	 * Currently displays game object information as text.
	 */
	public MapView() 
	{
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.GREEN));
		this.setLayout(new BorderLayout());
		this.getAllStyles().setBgColor(ColorUtil.BLACK);
		this.getAllStyles().setBgTransparency(255);
	}

	@Override
	public void update(Observable observable, Object data) 
	{
		gwProxy = (IGameWorld) data;
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		IIterator iterator = gwProxy.getCollection().getIterator();
		
		while (iterator.hasNext())
		{
			GameObject curObject = iterator.getNext();
			if (curObject instanceof IDrawable)
			{
//				pCmpRelPrnt.setX(getParent().getAbsoluteX());
//				pCmpRelPrnt.setY(getParent().getAbsoluteY());
				((IDrawable) curObject).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	@Override
	public void pointerPressed(int x, int y)
	{
		px = x - getParent().getAbsoluteX();
		py = y - getParent().getAbsoluteY();
		
		Point pPtrRelPrnt = new Point(px, py);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		IIterator iterator = gwProxy.getCollection().getIterator();
		
		while (iterator.hasNext())
		{
			GameObject curObj = iterator.getNext();
			if (curObj instanceof ISelectable)
			{
				ISelectable selectObj = (ISelectable)curObj;
				
				if (selectObj.contains(pPtrRelPrnt, pCmpRelPrnt))
				{
					selectObj.setSelected(true);
				}
				else
				{
					selectObj.setSelected(false);
				}
			}
		}
		repaint();
		System.out.println("Pressed");
	}
	
	public void setPrefSize(int width, int height)
	{
		
		//Need to fix the size of the map
		this.setWidth(width);
		this.setHeight(height);
		
		System.out.println("Map view: width = " + getMapWidth() + " height = " + getMapHeight());
	}
	
	/**
	 * @return The width of map view
	 */
	public double getMapWidth()
	{
		return (double) this.getWidth();
	}
	
	/**
	 * @return The height of map view
	 */
	public double getMapHeight()
	{
		return (double) this.getHeight();
	}
}
