package com.mycompany.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface IDrawable 
{
	/**
	 * Draws the game objects in the game world on screen
	 * @param g - Graphics field associated with the object
	 * @param pCmpRelPrnt - The objects location in the world
	 */
	public void draw(Graphics g, Point pCmpRelPrnt);
}
