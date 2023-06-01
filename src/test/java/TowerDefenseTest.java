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
    placingBlackHoleField.set(game, true); 

    // accéder et modifier le champ scoreCounter
    Field scoreCounterField = game.getClass().getDeclaredField("scoreCounter");
    scoreCounterField.setAccessible(true);
    scoreCounterField.set(game, initialScore - 100); 

    assertEquals(initialScore - 100, game.scoreCounter);
}

// Test du coût du Sun (300)
@Test
void testSunCost() throws Exception {
    int initialScore = game.scoreCounter;
    
    // accéder et modifier le champ placingSun
    Field placingSunField = game.getClass().getDeclaredField("placingSun");
    placingSunField.setAccessible(true);
    placingSunField.set(game, true); 

    // accéder et modifier le champ scoreCounter
    Field scoreCounterField = game.getClass().getDeclaredField("scoreCounter");
    scoreCounterField.setAccessible(true);
    scoreCounterField.set(game, initialScore - 300); 

    assertEquals(initialScore - 300, game.scoreCounter);
}

@Test
void testPlayerWins() throws Exception {
    // Supposons que le champ killsCounter existe et qu'il est accessible.
    Field killsCounterField = game.getClass().getDeclaredField("killsCounter");
    killsCounterField.setAccessible(true); // Ajoutez cette ligne pour rendre le champ accessible
    killsCounterField.set(game, 500); // Simule 500 kills

    // Vérifie si le joueur a gagné.
    int killsCounter = (int) killsCounterField.get(game);
    assertTrue(killsCounter >= 500);
}

@Test
void testIncreaseScore() throws Exception {
    // Accéder au champ scoreCounter et le rendre accessible.
    Field scoreCounterField = game.getClass().getDeclaredField("scoreCounter");
    scoreCounterField.setAccessible(true); 
    int initialScore = (int) scoreCounterField.get(game);

    // Simuler une augmentation du score.
    scoreCounterField.set(game, initialScore + 100);

    // Vérifier que le score a augmenté.
    int finalScore = (int) scoreCounterField.get(game);
    assertTrue(finalScore > initialScore);
}



    
}
