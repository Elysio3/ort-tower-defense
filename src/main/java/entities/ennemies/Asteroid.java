package entities.ennemies;

import utilities.ImageLoader;
import utilities.PathPosition;


/**
 * This class creates a single comet enemy
 */
public class Asteroid extends Enemy {
	/**
	 * Constructor
	 */
	public Asteroid(PathPosition p) {
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("/main/resources/asteroid.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 2;
	}
	
}