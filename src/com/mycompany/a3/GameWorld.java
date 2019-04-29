package com.mycompany.a3;

import java.util.Observable;

import com.codename1.ui.Dialog;
import com.mycompany.interfaces.ICollider;
import com.mycompany.interfaces.IGameWorld;
import com.mycompany.interfaces.IIterator;
import com.mycompany.interfaces.IMoveable;
import com.mycompany.objects.*;
import com.mycompany.objects.Missile.MissileType;

//Model in MVC architecture
public class GameWorld extends Observable implements IGameWorld
{
	public enum EntityType { PLAYER, ASTEROID, ENEMY, MISSILE }
	private GameCollection collection;
	
	private int score;
	private int elapsedTime;
	private int missileCount;
	private int playerLives;
	
	private double mapWidth;
	private double mapHeight;
	
	private boolean gameOver;
	private boolean soundOn;
	
	public void init()
	{
		collection = new GameCollection();
		
		score = 0;
		elapsedTime = 0;
		missileCount = 0;
		playerLives = 3;
		
		gameOver = false;
		soundOn = true;
		
		InformObservers();
	}
	
	/**
	 * Invokes the notifyObservers call to update map view and points view
	 */
	private void InformObservers()
	{
		GameWorldProxy gwp = new GameWorldProxy(this);
		this.setChanged();
		this.notifyObservers(gwp);
		
		if (gameOver)
		{
			String gameOverTxt = "Thank you for playing but the game is over.\nPlease restart the program.";
			if (Dialog.show("Game Over!", gameOverTxt, "Ok", null))
			{
				System.exit(0);
			}
		}
	}
	
	/**
	 * Spawn an asteroid object in the game world
	 */
	public void SpawnAsteroid()
	{
		Asteroid ast = new Asteroid();
		ast.SetRandLocation(mapWidth, mapHeight);
		collection.add(ast);
		InformObservers();
	}
	
	/**
	 * Spawn a Non-Player Ship in the game world
	 */
	public void SpawnEnemy()
	{
		EnemyShip enemy = new EnemyShip();
		enemy.SetRandLocation(mapWidth, mapHeight);
		collection.add(enemy);
		InformObservers();
	}
	
	/**
	 * Spawn a player ship in the game world
	 */
	public void SpawnPlayer()
	{
		//ONLY ALLOWS FOR ONE PLAYER SHIP TO EXIST
		//THIS WAS IN ASSIGNMENT 1 SUBMISSION AS WELL
		//I noticed I was marked down for this yet I had this implemented
		if (!FindInstanceOfPlayer())
		{
			PlayerShip player = new PlayerShip();			
			missileCount = player.GetMissileCount();
			collection.add(player);
			player.SetLocation(mapWidth / 2, mapHeight / 2);
			InformObservers();
		}
		else
		{
			System.err.println("There is already an instance of player");
		}
	}
	
	/**
	 * Spawn a space station in the game world
	 */
	public void SpawnStation()
	{
		SpaceStation station = new SpaceStation();
		station.SetRandLocation(mapWidth, mapHeight);
		collection.add(station);
		InformObservers();
	}
	
	/**
	 * @param speedUp - A boolean value to determine if player is to increase or decrease in speed. True is speed up
	 */
	public void ChangeSpeed(boolean speedUp)
	{
		PlayerShip playerObj = FindPlayer();
		if (playerObj != null)
		{
			playerObj.AdjustSpeed(speedUp);
			InformObservers();
		}
	}
	
	/**
	 * @param turnRight - A boolean value to determine if we are to turn player left or right.
	 * True means we are to turn player right.
	 */
	public void TurnPlayer(boolean turnRight)
	{
		/* For reference the headings of the direction is as follows
		 * 
		 * 	North - direction = 0
		 * 	East - direction = 90
		 * 	South - direction = 180
		 * 	West - direction = 270
		 * 
		 * 	If turning left and direction is currently zero, heading directly north, change the 
		 * 	direction value to 359 instead. Value must always be positive and within the range
		 * 	of 0 and 359 both inclusive.
		 */
		PlayerShip playerObj = FindPlayer();
		if (playerObj != null)
		{
			if (turnRight)
			{
				//Rotate player clockwise (right)
				playerObj.Steer(10);
			}
			else
			{
				//Rotate player counter-clockwise (left)
				playerObj.Steer(-10);
			}
			InformObservers();
		}
	}
	
	/**
	 * Rotates the launcher attached to the PlayerShip. Accepts positive
	 * and negative inputs
	 * @param amount - the amount to rotate by; positive is clockwise rotation
	 */
	public void RotateLauncher(int amount)
	{
		PlayerShip playerObj = FindPlayer();
		if (playerObj != null) 
		{
			playerObj.ChangeLauncherDir(amount);
			InformObservers();
		}
	}
	
	/**
	 * Fires a missile from player if player is in game and has missiles to fire.
	 */
	public void FirePlayerMissile()
	{
		PlayerShip playerObj = FindPlayer();
		if (playerObj != null)
		{
			if (playerObj.GetMissileCount() > 0)
			{				
				Missile missile = new Missile(playerObj.GetLauncherDir(), playerObj.GetSpeed() + 5, playerObj.GetFullLocation(), MissileType.PLAYER);
				collection.add(missile);
				playerObj.Fire();
				missileCount = playerObj.GetMissileCount();
			}
			else
			{
				System.err.println("No more missiles time to reload");
			}
			InformObservers();
		}
	}
	
	/**
	 * Fires a missile from Enemy Ship if one exists with missiles to fire.
	 * 
	 * obsolete?
	 */
	public void FireEnemymissile()
	{
		EnemyShip enemyObj = FindEnemyWithMissiles();
		if (enemyObj != null)
		{
			Missile missile = new Missile(enemyObj.GetLauncherDir(), enemyObj.GetSpeed() + 2, enemyObj.GetFullLocation(), MissileType.ENEMY);
			collection.add(missile);
			enemyObj.Fire();
			InformObservers();
		}
		else
		{
			System.err.println("Spawn a new enemy");
		}
	}
	
	/**
	 * Reset the player position to origin
	 */
	public void ResetPosition()
	{
		PlayerShip playerObj = FindPlayer();
		if (playerObj != null) 
		{
			playerObj.ResetPosition(mapWidth / 2.0, mapHeight / 2.0);
			InformObservers();
		}
	}
	
	/**
	 * Refuels the currently selected missile to its maximum fuel value.
	 */
	public void RefuelMissile()
	{
		IIterator iterator = collection.getIterator();
		
		while (iterator.hasNext())
		{
			GameObject curObj = iterator.getNext();
			
			if (curObj instanceof Missile && ((Missile) curObj).isSelected())
			{
				((Missile)curObj).ResetFuel();
				break;
			}
		}
	}
	
	/**
	 * Advance the game forward by one frame
	 */
	public void AdvanceGameClock(int elapsedTime)
	{
		IIterator iterator = collection.getIterator();
		//Handles movement of objects in the world
		while (iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if (obj instanceof IMoveable)
			{
				IMoveable moveObj = (IMoveable) obj;
				moveObj.Move(mapWidth, mapHeight);
				if (moveObj instanceof Missile)
				{
					Missile missileObj = (Missile) moveObj;
					missileObj.DecreaseFuel();
					if (missileObj.GetFuel() == 0)
					{
						iterator.remove();
					}
				}
			}
			else if (obj instanceof SpaceStation)
			{
				SpaceStation stationObj = (SpaceStation) obj;
				if (this.elapsedTime % 1000 == 0)
				{					
					stationObj.IncreaseBlinkTime();
				}
			}
		}
		
		CheckCollisions();
		
		this.elapsedTime += elapsedTime;
		InformObservers();
	}
	
	/**
	 * Runs through list to check for collisions after movements is made
	 */
	private void CheckCollisions()
	{
		//Time to re-evaluate the collection for collisions
		
		IIterator iterator = collection.getIterator();
		while (iterator.hasNext())
		{
			GameObject thisObj = iterator.getNext();
			if (thisObj instanceof ICollider)
			{
				ICollider thisColliderObj = (ICollider) thisObj;
				
				IIterator otherIterator = collection.getIterator();
				while (otherIterator.hasNext())
				{
					GameObject otherObj = otherIterator.getNext();
					if (otherObj instanceof ICollider && !(thisObj.equals(otherObj)))
					{
						ICollider otherColliderObj = (ICollider) otherObj;
						
						if (thisColliderObj.collidesWith(otherColliderObj))
						{
							System.out.println("Collision");
							thisColliderObj.handleCollision(otherColliderObj);

						}
					}
				}
			}
		}
		
		RemoveFlaggedObjects();
	}
	
	/**
	 * Temporary removal system to remove collided objects
	 */
	private void RemoveFlaggedObjects()
	{
		IIterator flagRemoval = collection.getIterator();
		
		while(flagRemoval.hasNext())
		{
			GameObject obj = flagRemoval.getNext();
			if (obj instanceof ICollider && ((ICollider)obj).getCollisionFlag())
			{
				flagRemoval.remove(obj);
				if (obj instanceof PlayerShip)
				{
					ReduceLives();
					if (playerLives > 0)
					{						
						SpawnPlayer();
					}
				}
				else if (obj instanceof Missile)
				{
					if (((Missile)obj).GetType() == MissileType.PLAYER)
					{
						score += ((Missile)obj).GetScoreGained();
					}
				}
			}
		}
	}
	
	/**
	 * Reduces number of player lives left. If that value hits zero, set gameOver to true and prevent further action
	 */
	public void ReduceLives()
	{
		playerLives--;
		
		if (playerLives == 0)
		{
			gameOver = true;
		}
		InformObservers();
	}
	
	/**
	 * When called searches through the collection to find an instance of PlayerShip
	 * @return Reference to PlayerShip location in collection if it exists, null otherwise.
	 */
	private PlayerShip FindPlayer()
	{
		IIterator iterator = collection.getIterator();
		PlayerShip temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof PlayerShip)
			{
				temp = (PlayerShip) curObj;
				break;
			}
		}
		
		if (temp == null)
		{
			System.err.println("No player ship has been spawned yet"); 
			return null;
		}
		else { return temp; }
	} 
	
	/**
	 * When called searches through the collection to find an instance of EnemyShip similar to FindEnemy(),
	 * the difference with this method, however, is that it searches for an EnemyShip with a missile count
	 * over zero. Meant purely for firing missiles from enemy ship.
	 * @return Reference to EnemyShip location in collection if it exists and has missiles to fire, null otherwise.
	 */
	private EnemyShip FindEnemyWithMissiles()
	{
		IIterator iterator = collection.getIterator();
		EnemyShip temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof EnemyShip)
			{
				temp = (EnemyShip) curObj;
				if (temp.GetMissileCount() > 0)
				{
					break;					
				}
				else
				{
					temp = null;
				}
			}
		}
		
		if (temp == null) 
		{
			System.err.println("No enemy ship has been spawned yet or there are no ships with missiles to fire."); 
			return null;
		}
		else { return temp; }
	}
	
	/**
	 * @return true if there exists an instance of player and lives are not 0, false otherwise.
	 */
	private boolean FindInstanceOfPlayer()
	{
		if (collection.getSize() > 0 && !gameOver)
		{
			IIterator iterator = collection.getIterator();
			while (iterator.hasNext())
			{
				if (iterator.getNext() instanceof PlayerShip)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Sets the width of the game world
	 * @param width - width of the map view to spawn objects in
	 */
	public void setGameWorldWidth(double width)
	{
		this.mapWidth = width;
	}
	
	/**
	 * @return The width of the game world
	 */
	public double getGameWorldWidth()
	{
		return this.mapWidth;
	}
	
	/**
	 * Sets the height of the game world
	 * @param height - height of the map view to spawn objects in
	 */
	public void setGameWorldHeight(double height)
	{
		this.mapHeight = height;
	}
	
	/**
	 * @return The height of the game world
	 */
	public double getGameWorldHeight()
	{
		return this.mapHeight;
	}
	
	/**
	 * Inverts the current sound setting. If on turn off, vice versa.
	 */
	public void changeSoundSetting()
	{
		soundOn = !soundOn;
		InformObservers();
	}

	@Override
	public int getPoints() 
	{
		return score;
	}

	@Override
	public int getMissileCount() 
	{
		return missileCount;
	}

	@Override
	public int getTime() 
	{
		return elapsedTime;
	}

	@Override
	public int getLives() 
	{
		return playerLives;
	}

	@Override
	public boolean getSoundSetting() 
	{
		return soundOn;
	}

	@Override
	public GameCollection getCollection() 
	{
		return this.collection;
	}
}