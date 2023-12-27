package org.cis1200.hans3.pixelgolf.utils;

import org.cis1200.hans5.pixelgolf.course.GolfCourse;
import org.cis1200.hans1.pixelgolf.Player;

public class GameManager {
    private GameState gameState;
    private GolfCourse golfCourse;
    private Player player;
    private static final int TOTAL_HOLES = 5;

    public GameManager(GolfCourse course) {
        this.golfCourse = course;
        this.gameState = new GameState();
        startNewGame();
    }

    public void startNewGame() {
        gameState.setCurrentHoleNumber(0);
        player = golfCourse.getPlayer();
        gameState.setCurrentPlayer(player);
        golfCourse.initializeCourse();
    }

    public void nextHole() {
        int nextHole = gameState.getCurrentHoleNumber() + 1;
        gameState.setCurrentHoleNumber(nextHole);
        if (nextHole <= TOTAL_HOLES) {
            golfCourse.generateNewHole(); // Generate a new hole
        }
    }

    public void updateGameState() {
        // Update the GameState fields with the current state of the game
        gameState.setPlayer(golfCourse.getPlayer());
        gameState.setScore(golfCourse.getPlayer().getScore());
        gameState.setCurrentHoleX(golfCourse.getCurrentHole().getPositionX());
        gameState.setCurrentHoleY(golfCourse.getCurrentHole().getPositionY());
        gameState.setBallPositionX(golfCourse.getBall().getPositionX());
        gameState.setBallPositionY(golfCourse.getBall().getPositionY());
        gameState.getObstacleData().clear();
        gameState.setObstacleData(golfCourse.serializeObstacles());
    }

    public boolean isGameFinished() {
        return gameState.getCurrentHoleNumber() >= TOTAL_HOLES;
    }

    public GameState getGameState() {
        golfCourse.loadObstacles(gameState.getObstacleData());
        return gameState;
    }

    public GolfCourse getGolfCourse() {
        return golfCourse;
    }

    public Player getPlayer() {
        return this.player;
    }
}
