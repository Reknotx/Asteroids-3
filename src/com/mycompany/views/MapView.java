package com.mycompany.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextArea;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.GameWorldProxy;
import com.mycompany.interfaces.IDrawable;
import com.mycompany.interfaces.IGameWorld;
import com.mycompany.interfaces.IIterator;

//View in MVC architecture
public class MapView extends Container implements Observer 
{	
	private IGameWorld gwProxy;
	
	/**
	 * Creates a container that will display the game objects later in graphical form.
	 * Currently displays game object information as text.
	 */
	public MapView() 
	{
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.GREEN));
		this.setLayout(new BorderLayout());
				
//		this.setWidth(1024);
//		this.setHeight(768);
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
		
		Point pCmpRelPrnt = new Point(0, 0);
		Object curObject = new Object();
		IIterator iterator = gwProxy.getCollection().getIterator();
		
		while (iterator.hasNext())
		{
			curObject = iterator.getNext();
			if (curObject instanceof IDrawable)
			{
//				pCmpRelPrnt.setX(getParent().getAbsoluteX());
//				pCmpRelPrnt.setY(getParent().getAbsoluteY());
				((IDrawable) curObject).draw(g, pCmpRelPrnt);
			}
		}
//		g.setColor(ColorUtil.BLACK);
//		g.drawRect(x - getParent().getAbsoluteX(), y - getParent().getAbsoluteY(), 30, 30);
//		g.fillRect(x - getParent().getAbsoluteX(), y - getParent().getAbsoluteY(), 30, 30);
	}
	
	public void setPrefSize(int width, int height)
	{
		this.setWidth(width);
		this.setHeight(height);
		
		System.out.println("width = " + getMapWidth() + " height = " + getMapHeight());
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
