package com.mycompany.objects;

public class EnemyShip extends Ship
{
	private int size;
	private MissileLauncher launcher;
	
	/**
	 * Creates an enemy ship object in the world. Enemy ships have two sizes, small and large.
	 * Enemies also have missiles that they can fire, a total of two.
	 */
	public EnemyShip()
	{
		super(2);
		launcher = new MissileLauncher(super.GetDirection());
		size = (rng.nextInt(2) + 1) * 10;
		SetColor(255, 0, 0);
	}
	
	/**
	 * @return integer value representing the size (10 = small / 20 = large)
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * 
	 * @return The direction of the enemy launcher, same as ship direction
	 */
	public int GetLauncherDir()
	{
		return launcher.GetLauncherDir();
	}
	
	public String toString()
	{
		String parentString = super.toString();
		String thisString = " size = " + size;
		return "Non-Player Ship: " + parentString + thisString;
	}
}
