package com.mycompany.objects;

import com.mycompany.interfaces.ISteerable;

public class PlayerShip extends Ship implements ISteerable
{
	private MissileLauncher launcher;
	
	/**
	 * The player ship that the user will control
	 */
	public PlayerShip()
	{
		super(10);
		launcher = new MissileLauncher(0);
		SetLocation(512.0, 384.0);
		SetSpeed(0);
		SetDirection(0);
		SetColor(0, 255, 255);
	}
	
	/**
	 * 
	 * @return the direction of the launcher on ship
	 */
	public int GetLauncherDir()
	{
		return launcher.GetLauncherDir();
	}
	
	/**
	 * Refill player missile silo back to full
	 */
	public void Reload()
	{
		super.SetMissileCount(10);
	}
	
	/**
	 * Resets the player's position in the gameworld back to spawn.
	 */
	public void ResetPosition()
	{
		SetLocation(512.0, 384.0);
	}
	
	/**
	 * 
	 * @param increase - if true increase the speed of ship, decrease if false
	 */
	public void AdjustSpeed(boolean increase)
	{
		if (increase && GetSpeed() < 15)
		{
			//Increase speed
			SetSpeed(GetSpeed() + 1);
		}
		else if (!increase && GetSpeed() > 0)
		{
			//Decrease speed
			SetSpeed(GetSpeed() - 1);
		}
	}
	
	@Override
	public void Steer(int amount) 
	{
		if (amount < 0 && GetDirection() + amount < 0)
		{
			/*Amount, which is a negative number, is added to current direction
			 * which is the same as subtracting from direction. We will have a negative
			 * number. Then add 360 to that number to get it's proper position.
			 */
			SetDirection(GetDirection() + amount + 360);
		}
		else
		{
			if (GetDirection() + amount >= 360)
			{
				SetDirection(GetDirection() + amount - 360);
			}
			else
			{
				SetDirection(GetDirection() + amount);			
			}
		}
	}
	
	/**
	 * @param amount - value to change the launcher direction by
	 */
	public void ChangeLauncherDir(int amount)
	{
		launcher.Steer(amount);
	}

	public String toString()
	{
		String parentString = super.toString();
		return "Player Ship: " + parentString + launcher.toString();
	}
}
