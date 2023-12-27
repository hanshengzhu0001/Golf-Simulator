package org.cis1200.hans3.pixelgolf.utils;

import org.cis1200.hans1.pixelgolf.Player;
import java.util.*;

public class GameState {
    private Player currentPlayer;
    private int currentHoleNumber;
    private float currentHoleX;
    private float currentHoleY;
    private float ballPositionX;
    private float ballPositionY;
    private List<String> obstacleData;

    // Default Constructor
    public GameState() {
        this.currentPlayer = new Player();
        this.currentHoleNumber = 0;
        this.currentHoleX = 0;
        this.currentHoleY = 0;
        this.ballPositionX = 0;
        this.ballPositionY = 0;
        this.obstacleData = new ArrayList<>();
    }

    public String toCustomString() {
        StringJoiner sj = new StringJoiner(",");
        System.out.println("Current Player Score (before saving): " + currentPlayer.getScore());
        if (currentPlayer != null) {
            sj.add(String.valueOf(currentPlayer.getScore()));
        } else {
            sj.add("0"); // Default score or handle this case as needed
        }
        sj.add(String.valueOf(currentHoleNumber));
        sj.add(String.valueOf(currentHoleX));
        sj.add(String.valueOf(currentHoleY));
        sj.add(String.valueOf(ballPositionX));
        sj.add(String.valueOf(ballPositionY));

        StringJoiner obstacleJoiner = new StringJoiner(";");
        for (String obstacle : obstacleData) {
            obstacleJoiner.add(obstacle);
        }
        sj.add(obstacleJoiner.toString());

        return sj.toString();
    }

    public static GameState fromCustomString(String str) {
        // Split the string to get the main game state and the obstacle data
        String[] mainAndObstacleData = str.split("(?<=\\d),(?=[a-zA-Z])", 2);

        // Split the main game state parts
        String[] parts = mainAndObstacleData[0].split(",");

        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid game state string");
        }

        // Parse basic game state
        GameState state = new GameState();
        state.setScore(Integer.parseInt(parts[0]));
        state.setCurrentHoleNumber(Integer.parseInt(parts[1]));
        state.setCurrentHoleX(Float.parseFloat(parts[2]));
        state.setCurrentHoleY(Float.parseFloat(parts[3]));
        state.setBallPositionX(Float.parseFloat(parts[4]));
        state.setBallPositionY(Float.parseFloat(parts[5]));

        // Check if there is obstacle data
        if (mainAndObstacleData.length > 1) {
            // Split the obstacle data parts
            String[] obstacleParts = mainAndObstacleData[1].split(";");
            for (String obstacleString : obstacleParts) {
                String[] obstacleData = obstacleString.split(",");
                if (obstacleData.length == 5) {
                    state.addObstacleData(obstacleString);
                } else {
                    throw new IllegalArgumentException("Invalid obstacle data format");
                }
            }
        }

        return state;
    }

    // Getters and setters for new fields (ball, obstacles, holes)
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentHoleNumber() {
        return currentHoleNumber;
    }

    public void setCurrentHoleNumber(int currentHoleNumber) {
        this.currentHoleNumber = currentHoleNumber;
    }

    public float getCurrentHoleX() {
        return currentHoleX;
    }
    public void setCurrentHoleX(float x) {
        this.currentHoleX = x;
    }

    public float getCurrentHoleY() {
        return currentHoleY;
    }
    public void setCurrentHoleY(float y) {
        this.currentHoleY = y;
    }

    public float getBallPositionX() {
        return ballPositionX;
    }
    public void setBallPositionX(float x) {
        this.ballPositionX = x;
    }

    public float getBallPositionY() {
        return ballPositionY;
    }
    public void setBallPositionY(float y) {
        this.ballPositionY = y;
    }

    public Player getPlayer() {
        return currentPlayer;
    }

    public void setPlayer(Player player) {
        this.currentPlayer = player;
    }

    public int getScore() {
        return currentPlayer.getScore();
    }

    public void setScore(int score) {
        currentPlayer.setScore(score);
    }

    // Method to add an obstacle's data
    public void setObstacleData(List<String> obstacleData) {
        this.obstacleData = obstacleData;
    }

    // Method to get obstacles data
    public List<String> getObstacleData() {
        return obstacleData;
    }

    public void addObstacleData(String data) {
        obstacleData.add(data);
    }
}

