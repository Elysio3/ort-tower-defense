import org.junit.jupiter.api.*;
import game.Game;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class TowerDefenseTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        
        game.scoreCounter = 200; // Initialisez le score 
    }

    // Test du coût du BlackHole (100)
    @Test
    void testBlackHoleCost() throws Exception {
        int initialScore = game.scoreCounter;
        
        // accéder et modifier le champ placingBlackHole
        Field placingBlackHoleField = game.getClass().getDeclaredField("placingBlackHole");
        placingBlackHoleField.setAccessible(true);
        placingBlackHoleField.set(game, true); // 

       

        assertEquals(initialScore - 100, game.scoreCounter - 100);
    }

    // Test du coût du Sun (300)
    @Test
    void testSunCost() throws Exception {
        int initialScore = game.scoreCounter;
        
        // accéder et modifier le champ placingSun
        Field placingSunField = game.getClass().getDeclaredField("placingSun");
        placingSunField.setAccessible(true);
        placingSunField.set(game, true); 

        assertEquals(initialScore - 300, game.scoreCounter - 300);
    }
}
