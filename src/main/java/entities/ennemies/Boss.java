package entities.ennemies;

import utilities.ImageLoader;
import utilities.PathPosition;


/**
 * This class creates a single alien enemy
 */
public class Boss extends Enemy {

	private int health;
	/**
	 * Constructor
	 */
	public Boss(PathPosition p) {
		this.health = 10; // le boss a 10pv
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("Boss.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 1;
	}

	/**
	 * hit the boss with 1 damage
	 * @return true if the boss is dead, false if still alive
	 */
	public boolean hit() {
		this.health--;
		if(this.health <= 0) {
			return true;
		} else {
			return false;
		}
	}

}
