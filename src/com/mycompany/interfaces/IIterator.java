package com.mycompany.interfaces;

import com.mycompany.objects.GameObject;

public interface IIterator 
{
	/**
	 * Searches list to see if there are objects remaining to be read
	 * @return true if there are items left, false otherwise
	 */
	public boolean hasNext();
	
	/**
	 * Receives the next item in the list to be read
	 * @return The next game object in the list
	 */ 
	public GameObject getNext();
	
	/**
	 * Removes the item at the current index and decreases index by one. To be
	 * used in conjunction with getNext when searching for objects.
	 */
	public void remove();
	
	/**
	 * Removes the passed object from collection as long as an instance of it occurs in the collection.
	 * @param o - The object to be removed
	 */
	public void remove(GameObject o);
}
