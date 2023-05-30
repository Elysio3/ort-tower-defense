package entities.towers;

import game.Game;
import utilities.Coordinate;
import entities.enemies.Enemy;
import utilities.ImageLoader;
import effects.MissileExplosion;

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
        this.cost = 60; // Set the cost of the Missile tower to 60
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

        List<Enemy> enemies = game.getEnemies();

        for (Enemy e : enemies) {
            Coordinate enemyPos = e.getPosition().getCoordinate();
            double dx = enemyPos.x - position.x;
            double dy = enemyPos.y - position.y;
            double dist = Math.sqrt((dx * dx) + (dy * dy));

            Coordinate pos = new Coordinate(position.x, position.y);

            // If the enemy is within range, attack
            if (dist < 80) {
                MissileExplosion explosion = new MissileExplosion(pos, enemyPos);
                game.getEffects().add(explosion);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}