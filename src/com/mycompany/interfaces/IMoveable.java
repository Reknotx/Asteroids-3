package com.mycompany.interfaces;

public interface IMoveable 
{
	/**
	 * All moveable objects are required to move based on current speed and direction
	 * @param mapWidth - The maximum x value for the gameworld
	 * @param mapHeight - The maximum y value for the gameworld
	 */
	public void Move(double mapWidth, double mapHeight);
}
