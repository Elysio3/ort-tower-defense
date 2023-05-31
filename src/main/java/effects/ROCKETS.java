package effects;

import utilities.Coordinate;
import utilities.ImageLoader;


/**
 * game.Game effect when the Missile attacks
 */
public class ROCKETS extends Effect
{
	public ROCKETS(Coordinate pos, Coordinate target) {
		// Loads ROCKET image
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("ROCKET.png");
		
		// X and Y position of Effect
		this.posX = pos.x;
		this.posY = pos.y;		
		
		// X and Y position of target enemy
		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;
		
		// Sets time to 0
		this.ageInSeconds = 0;
	}	
}