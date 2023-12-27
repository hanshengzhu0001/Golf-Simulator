package org.cis1200.hans1.pixelgolf;

import javax.swing.Timer;

import org.cis1200.hans5.pixelgolf.course.GolfCourse;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans3.pixelgolf.utils.GameManager;
import org.cis1200.hans3.pixelgolf.utils.GameSaver;
import org.cis1200.hans3.pixelgolf.utils.GameState;
import org.cis1200.hans5.pixelgolf.course.Club;
import org.cis1200.hans2.pixelgolf.ui.CoursePanel;

public class GameController {
    private GolfCourse golfCourse;
    private Player player;
    private Ball ball;
    private GameManager gameManager;
    private GameSaver gameSaver;
    private GameState gameState;
    private CoursePanel coursePanel;
    private Club club;
    private BigGame game; // Reference to the Game class
    private boolean isClubVisible = true;
    private Timer clubVisibilityTimer;

    public GameController(BigGame game) {
        this.game = game;
        this.golfCourse = new GolfCourse();
        this.player = golfCourse.getPlayer();
        this.ball = golfCourse.getBall(); // Assuming GolfCourse provides the ball
        this.gameManager = new GameManager(golfCourse);
        this.gameState = new GameState();
        this.gameSaver = new GameSaver();
        this.club = golfCourse.getClub();

        // Initialize the timer with 100 ms delay and action to hide the club
        clubVisibilityTimer = new Timer(100, e -> isClubVisible = false);
        clubVisibilityTimer.setRepeats(false); // The timer should only run once
    }

    public int getCourseWidth() {
        return golfCourse.getWidth(); // Assuming GolfCourse provides the width
    }

    public void updateGame() {
        golfCourse.updateCourseState();

        // Check if the hole is completed
        if (golfCourse.isHoleCompleted()) {
            gameState.setCurrentHoleNumber(gameState.getCurrentHoleNumber() + 1);
            player.incrementScore(1);
            gameManager.nextHole();
            player.nextHole();
        }

        // Notify Game class to update camera position after updating the game state
        game.updateAfterBallMove();
    }

    public int getBallPositionX() {
        return (int) ball.getPositionX();
    }


    // Handle player actions like shooting, moving, adjusting power, etc.
    public void handlePlayerAction(Action action, double value) {
        switch (action) {
            case SHOOT:
                club.swing(ball); // Swing the club and hit the ball
                isClubVisible = true; // Make the club visible
                clubVisibilityTimer.start(); // Start the timer for 0.3 seconds
                golfCourse.clearTrajectory();
                break;
            case ADJUST_POWER:
                club.setPower((float)value); // Update the power of the club
                golfCourse.updateTrajectory();
                break;
            case ADJUST_ANGLE:
                club.setAngle((float)value); // Update the angle of the club
                golfCourse.updateTrajectory();
                break;
            default:
                break;
        }
    }

    public enum Action {
        SHOOT,
        ADJUST_POWER,
        ADJUST_ANGLE
    }

    public void saveCurrentGameState() {
        gameManager.updateGameState(); // Update the game state before saving
        GameState currentState = gameManager.getGameState();
        gameSaver.saveGameState(currentState); // Save the updated game state
    }
    public void loadGame(GameState loadedState) {
        this.gameState.setCurrentHoleNumber(loadedState.getCurrentHoleNumber());
        this.golfCourse.setBallPosition(loadedState.getBallPositionX(),
                loadedState.getBallPositionY());
        this.golfCourse.setCurrentHole(loadedState.getCurrentHoleX(),
                loadedState.getCurrentHoleY());
        this.player.setScore(loadedState.getScore());

        for (String obstacleData : loadedState.getObstacleData()) {
            golfCourse.addObstacleFromString(obstacleData);
        }
        game.updateAfterBallMove(); // Refresh the view with the loaded game state
    }

    public boolean isClubVisible() {
        return isClubVisible;
    }

    // Getters for game state, score, and other attributes
    public int getCurrentPlayerScore() {
        return player.getScore();
    }

    public GameState getGameState() {
        return gameManager.getGameState();
    }

    public GolfCourse getGolfCourse() {
        return golfCourse;
    }

    public boolean isGameFinished() {
        return gameManager.isGameFinished();
    }

    // ... Additional methods as needed ...
}

