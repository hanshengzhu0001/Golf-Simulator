package org.cis1200.hans1.pixelgolf;

import org.cis1200.hans2.pixelgolf.ui.*;
import javax.swing.*;
import org.cis1200.hans3.pixelgolf.utils.*;
import org.cis1200.hans5.pixelgolf.course.GolfCourse;

public class BigGame implements Runnable {
    private GameWindow gameWindow;
    private GameController gameController;
    private GameManager gameManager;
    private long startTime;
    private boolean isRunning;
    private int cameraX; // Camera's horizontal position

    @Override
    public void run() {
        initializeGame();
        startGame();
    }

    private void initializeGame() {
        gameManager = new GameManager(new GolfCourse());
        gameController = new GameController(this);
        gameWindow = new GameWindow(gameController, gameController.getGolfCourse());
        startTime = System.currentTimeMillis();
        cameraX = 0; // Initialize camera position
    }

    private void startGame() {
        SwingUtilities.invokeLater(() -> {
            gameWindow.initializeWindow();
            isRunning = true;
            gameLoop();
        });
    }

    private void gameLoop() {
        showStartGamePopup();
        int delay = 16; // Roughly 60 frames per second
        new Timer(delay, e -> {
            gameController.updateGame();
            gameManager.updateGameState();
            updateCameraPosition();
            gameWindow.refreshDisplay(cameraX);

            long elapsed = System.currentTimeMillis() - startTime;
            gameWindow.updateTimer(elapsed);

            if (!isRunning || gameController.isGameFinished()) {
                ((Timer) e.getSource()).stop();
                showEndGamePopup(elapsed);
            }
        }).start();
    }

    private void updateCameraPosition() {
        int ballPositionX = gameController.getBallPositionX();
        int screenWidth = gameWindow.getWidth();
        cameraX = ballPositionX - screenWidth / 2;
        cameraX = Math.max(cameraX, 0);
        cameraX = Math.min(cameraX, gameController.getCourseWidth() - screenWidth);
    }

    private void showStartGamePopup() {
        String message = "You will be trying your best to shoot the golf ball at different \n"
                + "power and angle, and making them into holes along the map. \n" +
                "Make sure to avoid the obstacles: trees deflect your ball entirely, \n"
                + "water resets your ball's position, sand slows down the ball rapidly. \n"
                + "Try to reach the target score within the shortest time frame!";
        JOptionPane.showMessageDialog(gameWindow, message,
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEndGamePopup(long elapsedMillis) {
        int seconds = (int) (elapsedMillis / 1000) % 60;
        int minutes = (int) (elapsedMillis / (1000 * 60)) % 60;
        String message = "Congratulations!\nTime Taken: "
                + minutes + " minutes and " + seconds + " seconds";
        JOptionPane.showMessageDialog(gameWindow, message,
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public void endGame() {
        isRunning = false;
    }

    public void updateAfterBallMove() {
        updateCameraPosition();
        gameWindow.refreshDisplay(cameraX);
    }

    // Main method to start the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BigGame());
    }
}
