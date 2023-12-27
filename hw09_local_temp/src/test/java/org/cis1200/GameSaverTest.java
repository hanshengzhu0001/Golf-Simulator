package org.cis1200;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans3.pixelgolf.utils.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameSaverTest {
    private static final String SAVE_FILE_PATH = "game_save.txt";
    private GameSaver gameSaver;
    private GameState originalState;

    @BeforeEach
    public void setUp() {
        gameSaver = new GameSaver();
        originalState = new GameState();
        // Initialize the game state with some values
        originalState.setScore(5);
        originalState.setCurrentHoleNumber(3);
        originalState.setCurrentHoleX(100.0f);
        originalState.setCurrentHoleY(150.0f);
        originalState.setBallPositionX(200.0f);
        originalState.setBallPositionY(250.0f);
        // Add obstacle data or other necessary state information
    }

    @Test
    public void testSaveAndLoadGameState() {
        // Save the state
        gameSaver.saveGameState(originalState);

        // Load the state
        GameState loadedState = gameSaver.loadGameState();

        // Assert that the loaded state matches the original state
        assertNotNull(loadedState, "Loaded game state should not be null.");
        assertEquals(originalState.getScore(), loadedState.getScore(), "Scores should match.");
        assertEquals(originalState.getCurrentHoleNumber(), loadedState.getCurrentHoleNumber(),
                "Hole numbers should match.");
        assertEquals(originalState.getCurrentHoleX(), loadedState.getCurrentHoleX(),
                "Hole X positions should match.");
        assertEquals(originalState.getCurrentHoleY(), loadedState.getCurrentHoleY(),
                "Hole Y positions should match.");
        assertEquals(originalState.getBallPositionX(), loadedState.getBallPositionX(),
                "Ball X positions should match.");
        assertEquals(originalState.getBallPositionY(), loadedState.getBallPositionY(),
                "Ball Y positions should match.");
        // Add assertions for obstacle data or other state information as necessary
    }

    @AfterEach
    public void tearDown() {
        // Clean up by deleting the save file
        try {
            Path path = Paths.get(SAVE_FILE_PATH);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            fail("Failed to delete the save file in tearDown.");
        }
    }
}
