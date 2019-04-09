package com.mycompany.interfaces;

import com.mycompany.objects.GameObject;

public interface ICollection 
{
	/**
	 * Adds a new game object to the list
	 * @param o - The game object to be added
	 */
	public void add(GameObject o);
	
	/**
	 * Returns an iterator that reads the list in the collection so you don't have to manipulate the collection directly
	 * @return The iterator attached to the collection
	 */
	public IIterator getIterator();
	
	/**
	 * 
	 * @return the size of the list in the collection; size should be readonly
	 */
	public int getSize();
}
