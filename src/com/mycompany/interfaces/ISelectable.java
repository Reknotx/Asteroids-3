package com.mycompany.interfaces;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface ISelectable 
{
	/**
	 * Marks an object as selected
	 * @param yes - If true object is selected, false otherwise.
	 */
	public void setSelected(boolean yes);
	
	/**
	 * @return True if object is selected
	 */
	public boolean isSelected();
	
	/**
	 * Determines if a pointer is "in" an object
	 * @param pPtrRelPrnt - pointer position relative to the parent origin
	 * @param pCmpRelPrnt - component position relative to the parent origin
	 * @return True if pointer is in an object, false otherwise
	 */
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	
	/**
	 * Draws the objects that are selected
	 * @param g - Graphics associated with the selected object
	 * @param pCmpRelPrnt - Relative position of the object in world space
	 */
	public void draw(Graphics g, Point pCmpRelPrnt);
	
}
