package game.entities.ennemies;

import game.utilities.ImageLoader;
import game.utilities.PathPosition;

/**
 * This class creates a single comet enemy
 */
public class Comet extends Enemy {
	/**
	 * Constructor
	 */
	public Comet(PathPosition p) {
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/comet.png");
		this.position = p;
		this.anchorX = -25;
		this.anchorY = -25;
		this.velocity = 8;
	}
	
}
