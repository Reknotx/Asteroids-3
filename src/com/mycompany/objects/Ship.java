package com.mycompany.objects;

public abstract class Ship extends MoveableGameObject 
{
	// Will possibly be added to the project with player ship and enemy ship extending this class
	private int missileCount = 0;
	
	public Ship(int missiles)
	{
		missileCount = missiles;
	}
	
	/**
	 * 
	 * @return The missiles currently available in ship.
	 */
	public int GetMissileCount()
	{
		return missileCount;
	}
	
	/**
	 * Sets the current missile count for the ship. Used for reloading
	 * @param amount - amount to fill ship with
	 */
	public void SetMissileCount(int amount)
	{
		missileCount = amount;
	}
	
	/**
	 * @return The current number of missiles left in the ship
	 */
	public void Fire()
	{
		missileCount--;
	}
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = "missiles = " + missileCount + " ";
		return parentString + thisString;
	}
}
