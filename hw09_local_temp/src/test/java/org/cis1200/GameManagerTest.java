package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans3.pixelgolf.utils.GameManager;
import org.cis1200.hans5.pixelgolf.course.GolfCourse;
import org.cis1200.hans1.pixelgolf.Player;
import org.cis1200.hans3.pixelgolf.utils.GameState;

public class GameManagerTest {

    private GameManager gameManager;

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        gameManager = new GameManager(new GolfCourse());
        gameState = gameManager.getGameState();
    }

    @Test
    public void testNextHoleGeneration() {
        int currentHoleBefore = gameState.getCurrentHoleNumber();
        gameManager.nextHole();
        assertEquals(currentHoleBefore + 1, gameState.getCurrentHoleNumber());
    }

    @Test
    public void testGameCompletion() {
        for (int i = 0; i < 5; i++) {
            gameManager.nextHole();
        }
        assertTrue(gameManager.isGameFinished());
    }

    @Test
    public void testGameStateUpdate() {
        Player player = new Player();
        player.incrementScore(1);
        gameManager.getGameState().setCurrentPlayer(player);
        GameState gameState = gameManager.getGameState();
        assertEquals(1, gameState.getCurrentPlayer().getScore());
    }
}
