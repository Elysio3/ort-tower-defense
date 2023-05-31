package entities.towers;

import game.Game;
import utilities.Coordinate;
import entities.ennemies.Enemy;
import utilities.ImageLoader;
import effects.StarDust;

import java.util.List;

/**
 * This class creates a single missile tower
 */
public class Missile extends Tower {
    /**
     * Constructor
     */
    public Missile(Coordinate pos) {
        ImageLoader loader = ImageLoader.getLoader();
        this.tower = loader.getImage("MissileTOWER.png");
        this.position = pos;
        this.anchorX = -40;
        this.anchorY = -40;
    }

    /**
     * Interacts with the game and attacks enemies within range
     */
    public void interact(Game game, double deltaTime) {
        timeSinceLastFire += deltaTime;

        // If the time is less than 1 second, don't interact
        if (timeSinceLastFire < 1) {
            return;
        }

        List<Enemy> enemies = game.enemies;

        for (Enemy e : enemies) {
            Coordinate enemyPos = e.getPosition().getCoordinate();
            double dx = enemyPos.x - position.x;
            double dy = enemyPos.y - position.y;
            double dist = Math.sqrt((dx * dx) + (dy * dy));

            Coordinate pos = new Coordinate(position.x, position.y);

            // If the enemy is within range, attack
            if (dist < 80) {
                StarDust stardust = new StarDust(pos, enemyPos);
                game.effects.add(stardust);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}package entities.towers;

import game.Game;
import utilities.Coordinate;
import entities.ennemies.Enemy;
import utilities.ImageLoader;
import effects.ROCKETS;
import effects.StarDust;

import java.util.List;

/**
 * This class creates a single blackhole tower
 */
public class Missile extends Tower {
	/**
	 * Constructor
	 */
	public Missile(Coordinate pos) {
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("MissileTOWER.png");
		this.position = pos;
		this.anchorX = -43;
		this.anchorY = -37;
	}
	
	/**
	 * 
	 */
	public void interact(Game game, double deltaTime) {	// tracks time that effect has existed
		timeSinceLastFire += deltaTime;
		
		// if time less than 1.5 seconds, don't interact
		if(timeSinceLastFire < 1)
			return;
		
		List<Enemy> enemies = game.enemies; // new list of enemies
		
		// Gives position of an enemy in enemy list
		for(Enemy e: enemies)
		{	
			
			// holds position of enemy
			Coordinate enemyPos = e.getPosition().getCoordinate();

			// Compute distance of enemy to tower
			double dx, dy, dist;	// change in x, y, and total distance
			
			// calculates change in x and y position 
			dx = enemyPos.x - position.x; // x position of enemy - tower
			dy = enemyPos.y - position.y; // y position of enemy - tower
		
			// use Pythagorean theorem to calculate distance
			dist = Math.sqrt((dx*dx) + (dy*dy));
			
			// holds position of effect
			Coordinate pos = new Coordinate(position.x, position.y);	
			

			// if enemy is in range, fire salt
			if(dist < 80)
			{	ROCKETS rockets = new ROCKETS(pos, enemyPos);
				game.effects.add(rockets);
				timeSinceLastFire = 0;
				return;
			}	
		} 
	}	
}