package com.mycompany.a3;

import java.util.Observable;

import com.mycompany.interfaces.IGameWorld;

public class GameWorldProxy extends Observable implements IGameWorld 
{
	private GameWorld gwp;
	
	/**
	 * GameWorldProxy is created to send specific information to the views.
	 * Can be thought of as an information transfer object.
	 * @param gw - Reference to the gameworld to access the necessary info.
	 */
	public GameWorldProxy(GameWorld gw)
	{
		gwp = gw;
	}

	@Override
	public int getPoints() 
	{
		return gwp.getPoints();
	}

	@Override
	public int getMissileCount() 
	{
		return gwp.getMissileCount();
	}

	@Override
	public int getTime() 
	{
		return gwp.getTime() / 1000;
	}

	@Override
	public int getLives() 
	{
		return gwp.getLives();
	}

	@Override
	public boolean getSoundSetting() 
	{
		return gwp.getSoundSetting();
	}

	@Override
	public GameCollection getCollection() 
	{
		return gwp.getCollection();
	}
}
