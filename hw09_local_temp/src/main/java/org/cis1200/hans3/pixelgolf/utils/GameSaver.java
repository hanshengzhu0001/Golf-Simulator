package org.cis1200.hans3.pixelgolf.utils;

import java.io.*;

public class GameSaver {
    private static final String SAVE_FILE = "game_save.txt";

    //use toCustomString and fromCustomString in gameState
    public void saveGameState(GameState gameState) {
        try (FileWriter fileWriter = new FileWriter(SAVE_FILE);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(gameState.toCustomString());
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public GameState loadGameState() {
        try (FileReader fileReader = new FileReader(SAVE_FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String gameStateString = bufferedReader.readLine();
            return GameState.fromCustomString(gameStateString);
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
