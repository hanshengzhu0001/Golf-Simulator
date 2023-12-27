package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans1.pixelgolf.Player;
import org.cis1200.hans3.pixelgolf.utils.GameState;
public class GameStateTest {
    private GameState gameState;

    @BeforeEach
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void testPlayerScore() {
        Player player = new Player();
        player.incrementScore(1);
        gameState.setCurrentPlayer(player);
        assertEquals(1, gameState.getCurrentPlayer().getScore());
    }

    @Test
    public void testHolePosition() {
        gameState.setCurrentHoleX(10);
        gameState.setCurrentHoleY(20);
        assertEquals(10, gameState.getCurrentHoleX(), 0.001);
        assertEquals(20, gameState.getCurrentHoleY(), 0.001);
    }

    @Test
    public void testBallPosition() {
        // Set ball position in the game state
        gameState.setBallPositionX(50);
        gameState.setBallPositionY(100);

        // Assert that the ball position is correctly updated in the game state
        assertEquals(50, gameState.getBallPositionX(), 0.001,
                "Ball X position should be updated correctly.");
        assertEquals(100, gameState.getBallPositionY(), 0.001,
                "Ball Y position should be updated correctly.");
    }
}
