package com.mycompany.a3;

import java.util.ArrayList;

import com.mycompany.interfaces.ICollection;
import com.mycompany.interfaces.IIterator;
import com.mycompany.objects.GameObject;

public class GameCollection implements ICollection
{
	private ArrayList<GameObject> list;
	
	/**
	 * The collection that will store all game objects in the game world
	 */
	public GameCollection() 
	{
		list = new ArrayList<GameObject>();
	}

	@Override
	public void add(GameObject o) 
	{
		list.add(o);
	}

	@Override
	//Returns the iterator associated with the collection to traverse through the collection
	public IIterator getIterator() 
	{
		return new GameObjectIterator();
	}
	
	@Override
	public int getSize() 
	{
		return list.size();
	}

	private class GameObjectIterator implements IIterator
	{
		private int index;
		
		/**
		 * The iterator to traverse through the collection
		 */
		public GameObjectIterator()
		{
			index = -1;
		}

		@Override
		public boolean hasNext() {
			if (list.size() <= 0) return false;
			if (index == list.size() - 1) 
			{
				index = -1;
				return false;
			}
			return true;
		}

		@Override
		public GameObject getNext() {
			index++;
			return list.get(index);
		}

		@Override
		public void remove() 
		{
			list.remove(index);
			index--;
		}

		@Override
		public void remove(GameObject o) 
		{
			list.remove(o);
			index--;
		}
	}
}
