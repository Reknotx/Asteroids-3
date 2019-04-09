package com.mycompany.interfaces;

public interface ICollider 
{
	/**
	 * Determines with two objects collide with each other. 
	 * 
	 * This methods works with two objects. For this to work you call
	 * the collidesWith method on the currently examined object, lets
	 * say Asteroid, then pass a secondary object in the world, a missile.
	 * Missile becomes other. So you want to examine if the asteroid is
	 * colliding with the missile. So asteroid.collidesWith(missile)
	 * will be the call and then you handle the collisions if they are.
	 * 
	 * @param other - The secondary object to evaluate collision with.
	 * @return - True if collision occurs
	 */
	public boolean collidesWith(ICollider other);
	
	/**
	 * The polymorphic method that handles collisions with other objects.
	 * The same concept seen in collidesWith applies here.
	 * @param other - The secondary object to collide with.
	 */
	public void handleCollision(ICollider other);
}
