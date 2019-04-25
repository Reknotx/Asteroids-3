package com.mycompany.a3;

import java.util.ArrayList;
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
			missileCount = 10;
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
				Missile missile = new Missile(playerObj.GetLauncherDir(), playerObj.GetSpeed() + 2, playerObj.GetFullLocation(), MissileType.PLAYER);
				collection.add(missile);
				missileCount--;
				playerObj.Fire();
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
	 * Reload the player with more missiles up to max.
	 */
	public void ReloadMissiles()
	{
		//Reset missile count to 10
		PlayerShip playerObj = FindPlayer();
		SpaceStation stationObj = FindStation();
		if (playerObj != null && stationObj != null) 
		{ 
			playerObj.Reload();
			missileCount = 10;
			InformObservers();
		}
	}
	
	/**
	 * Used to destroy asteroids and enemy ships with missiles depending on the command.
	 * Will also increment the player score based on predetermined values depending on
	 * the enemy as well. A value of true indicates an asteroid was struck, a value of false
	 * indicates that an enemy ship was struck instead.
	 * @param entity - entity as listed in the EntityType enumeration. Select either asteroid or enemy ship
	 */
	public void DestroyEnemy(EntityType entity)
	{
		//Destroy asteroid or enemy ship with player missile
		//increment score based on which enemy was destroyed
		Missile missileObj = FindMissile(MissileType.PLAYER);
		GameObject enemy = null;
		if (missileObj != null) 
		{	
			switch (entity)
			{
				case ASTEROID:
					enemy = FindAsteroid();
					score += 10;
					break;
					
				case ENEMY:
					enemy = FindEnemy();
					score += 20;
					break;
					
				default:
					System.err.println("Wrong enemy selected");
			}
			
			if (enemy != null)
			{
				IIterator iterator = collection.getIterator();
				iterator.remove(missileObj);
				iterator.remove(enemy);
				InformObservers();
			}
		}
	}
	
	/**
	 * Destroys the player with an enemy missile if there is an instance of both.
	 */
	public void KillPlayerWithEnemyMissile()
	{
		Missile missileObj = FindMissile(MissileType.ENEMY);
		if (missileObj != null)
		{
			PlayerShip playerObj = FindPlayer();
			if (playerObj != null)
			{
				IIterator iterator = collection.getIterator();
				iterator.remove(playerObj);
				iterator.remove(missileObj);
				ReduceLives();
				InformObservers();
			}
		}
		
	}
	
	/**
	 * Handles all collision variations within the game world.
	 * 	- Player crashes into asteroid
	 *  - Player crashes into enemy ship
	 *  - Two asteroids collide together
	 *  - Asteroid collides with enemy ship
	 * @param entityOne - Determines collider, must be of type PLAYER or type ASTEROID
	 * @param entityTwo - Determines collidee, must be of type ASTEROID or type ENEMY
	 */
	public void Collision(EntityType entityOne, EntityType entityTwo)
	{
		//When two entities collide perform necessary actions based on 
		//what happened.
		/* Variations of collisions
		 * 	- Player crashes into asteroid
		 *  - Player crashes into enemy ship
		 *  - Two asteroids collide with each other
		 *  - Asteroid collides with enemy ship 
		 */
		GameObject objectOne = null;
		GameObject objectTwo = null;
		IIterator iterator = collection.getIterator();
		if (entityOne != null && entityTwo != null)
		{
			switch (entityOne)
			{
				case PLAYER:
					objectOne = FindPlayer();
					break;
					
				case ASTEROID:
					objectOne = FindAsteroid();
					break;
					
				default:
					System.err.println("Poor value passed");
					break;
			}
			
			if (objectOne != null)
			{
				switch (entityTwo)
				{
					case ASTEROID:
						while (iterator.hasNext())
						{
							Object curObj = iterator.getNext();
							if (curObj instanceof Asteroid)
							{
								objectTwo = (Asteroid) curObj;
								if (!objectTwo.equals(objectOne))
								{
									break;									
								}
								else
								{
									objectTwo = null;
								}
								
							}
						}
						break;
						
					case ENEMY:
						objectTwo = FindEnemy();
						break;
						
					default:
						System.err.println("Poor value passed");
						break;
				}
				
				if (objectTwo != null)
				{
					iterator.remove(objectOne);
					iterator.remove(objectTwo);
					if (objectOne instanceof PlayerShip)
					{
						ReduceLives();
					}
					InformObservers();
				}
				else
				{
					System.err.println("No instance of " + entityTwo);
				}
			}
			else
			{
				System.err.println("No instance of " + entityOne);
			}
		}
		else
		{
			System.err.println("A value of null was passed to method");
			System.err.println("Entity one = " + entityOne + "\nEntity two = " + entityTwo);
		}
	}
	
	/**
	 * Advance the game forward by one frame
	 */
	public void AdvanceGameClock()
	{
		IIterator iterator = collection.getIterator();
		//Handles movement of objects in the world
		while (iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if (obj instanceof IMoveable)
			{
				IMoveable moveObj = (IMoveable) obj;
				moveObj.Move();
				if (moveObj instanceof Missile)
				{
					Missile missileObj = (Missile) moveObj;
					if (missileObj.GetFuel() == 0)
					{
						iterator.remove();
					}
				}
			}
			else if (obj instanceof SpaceStation)
			{
				SpaceStation stationObj = (SpaceStation) obj;
				stationObj.IncreaseBlinkTime();
			}
		}
		
		CheckCollisions();
		
		elapsedTime += 20;
		InformObservers();
	}
	
	/**
	 * Runs through list to check for collisions after movements is made
	 */
	private void CheckCollisions()
	{
		//Time to re-evaluate the collection for collisions
		ArrayList<GameObject> collisionList = new ArrayList<GameObject>();
		
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
	 * When called searches through the collection to find an instance of Asteroid
	 * @return Reference to Asteroid location in collection if it exists, null otherwise.
	 */
	private Asteroid FindAsteroid()
	{
		IIterator iterator = collection.getIterator();
		Asteroid temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof Asteroid)
			{
				temp = (Asteroid) curObj;
				break;
			}
		}
		
		if (temp == null) 
		{
			System.err.println("No asteroid has been spawned yet"); 
			return null;
		}
		else { return temp; }
	}
	
	/**
	 * When called searches through the collection to find an instance of EnemyShip
	 * @return Reference to EnemyShip location in collection if it exists, null otherwise.
	 */
	private EnemyShip FindEnemy()
	{
		IIterator iterator = collection.getIterator();
		EnemyShip temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof EnemyShip)
			{
				temp = (EnemyShip) curObj;
				break;
			}
		}
		
		if (temp == null) 
		{
			System.err.println("No enemy ship has been spawned yet"); 
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
	 * When called searches through the collection to find an instance of Missile based on passed type.
	 * @param type - Determines the missile type to search for, either PLAYER or ENEMY
	 * @return Reference to Missile location in collection if type matches, null otherwise
	 */
	private Missile FindMissile(MissileType type)
	{
		IIterator iterator = collection.getIterator();
		Missile temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof Missile)
			{
				temp = (Missile) curObj;
				if (temp.GetType().equals(type))
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
			System.err.println("No missile of type " + type.toString() + " has been spawned yet"); 
			return null;
		}
		else { return temp; }
	}
	
	/**
	 * When called searches through the collection to find an instance of SpaceStation.
	 * @return Reference to the Station location in collection if one exists, null otherwise
	 */
	private SpaceStation FindStation()
	{
		IIterator iterator = collection.getIterator();
		SpaceStation temp = null;
		while (iterator.hasNext())
		{
			Object curObj = iterator.getNext();
			if (curObj instanceof SpaceStation)
			{
				temp = (SpaceStation) curObj;
				break;
			}
		}
		
		if (temp == null) 
		{
			System.err.println("No space station has been spawned yet"); 
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