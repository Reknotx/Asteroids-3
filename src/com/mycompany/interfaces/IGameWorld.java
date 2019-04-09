package com.mycompany.interfaces;

import com.mycompany.a3.GameCollection;

public interface IGameWorld 
{
	/**
	 * @return score  - The current game score defined as an integer value
	 */
	public int getPoints();
	
	/**
	 * @return missiles - The current missiles available in silo defined as an integer value 
	 */
	public int getMissileCount();
	
	/**
	 * @return time - The elapsed game time, defined as an integer value
	 */
	public int getTime();
	
	/**
	 * @return lives - The player's remaining lives 
	 */
	public int getLives();

	/**
	 * @return sound - Boolean value indicating if sound is on or off. True = On
	 */
	public boolean getSoundSetting();
	
	/**
	 * @return A reference to the game collection in the game world
	 */
	public GameCollection getCollection();
}
