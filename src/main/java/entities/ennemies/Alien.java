package entities.ennemies;

import utilities.ImageLoader;
import utilities.PathPosition;


/**
 * This class creates a single alien enemy
 */
public class Alien extends Enemy {
	/**
	 * Constructor
	 */
	public Alien(PathPosition p) {
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("/main/resources/Alien-Ship.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 6;
	}

}
