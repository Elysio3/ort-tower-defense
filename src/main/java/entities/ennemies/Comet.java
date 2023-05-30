package entities.ennemies;

import utilities.ImageLoader;
import utilities.PathPosition;


/**
 * This class creates a single comet enemy
 */
public class Comet extends Enemy {
	/**
	 * Constructor
	 */
	public Comet(PathPosition p) {
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("comet.png");
		this.position = p;
		this.anchorX = -25;
		this.anchorY = -25;
		this.velocity = 8;
	}
	
}
