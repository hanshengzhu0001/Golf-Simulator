package org.cis1200.hans5.pixelgolf.course;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.cis1200.hans1.pixelgolf.Player;
import org.cis1200.hans2.pixelgolf.ui.CoursePanel;
import org.cis1200.hans4.pixelgolf.physics.*;

public class GolfCourse {
    private Ball ball;
    private Club club;
    private List<Obstacle> obstacles;

    private CoursePanel coursePanel;
    private Hole currentHole;
    private int[][] courseGrid;
    private Random random;
    private Player player;
    private static final int RADIUS = Ball.RADIUS;
    private static final int CANVAS_SIZE = 600; // Height of the visible canvas
    private static final int COURSE_LENGTH = 10000; // Total length of the course
    private static final int TERRAIN_THICKNESS = CANVAS_SIZE / 5; // Thickness of the terrain
    private float groundLevel;
    private List<Point> trajectoryPoints;
    private PhysicsEngine physicsEngine;


    public GolfCourse() {
        groundLevel = CANVAS_SIZE - TERRAIN_THICKNESS;

        player = new Player();
        // Create the ball with the correct ground level
        ball = new Ball(0, groundLevel, groundLevel);

        club = new Club();

        obstacles = new ArrayList<>();
        random = new Random();
        physicsEngine = new PhysicsEngine();
        initializeCourse();
        trajectoryPoints = new ArrayList<>();
    }

    public void initializeCourse() {
        courseGrid = new int[COURSE_LENGTH][CANVAS_SIZE]; // Adjusted for extended course length
        obstacles.clear();
        placeObstaclesRandomly();
        placeHoleRandomly();
    }

    private void placeHoleRandomly() {
        int holeX = random.nextInt(COURSE_LENGTH - 2 * currentHole.RADIUS) + currentHole.RADIUS;
        int holeY = CANVAS_SIZE - TERRAIN_THICKNESS - currentHole.RADIUS;
        // Set the hole at the ground level
        currentHole = new Hole(holeX, holeY);

        // Update the course grid to reflect the hole's position
        for (int x = holeX - currentHole.RADIUS; x <= holeX + currentHole.RADIUS; x++) {
            for (int y = holeY - currentHole.RADIUS; y <= holeY + currentHole.RADIUS; y++) {
                courseGrid[x][y] = 2; // 2 represents the hole
            }
        }
    }

    public void placeObstaclesRandomly() {
        int numberOfObstacles = 5; // Adjust as needed
        for (int i = 0; i < numberOfObstacles; i++) {
            int width = random.nextInt(60) + 50; // Random width between 50 and 110
            int height = width * 2; // Maintain a fixed width-to-height ratio of 2:1

            int x = random.nextInt(COURSE_LENGTH);
            int y = CANVAS_SIZE - TERRAIN_THICKNESS;

            Obstacle obstacle;
            int obstacleType = random.nextInt(3); // Randomly select 0, 1, or 2
            switch (obstacleType) {
                case 0:
                    obstacle = new Tree(x, y, width, height);
                    break;
                case 1:
                    obstacle = new Water(x, y, width, height);
                    break;
                case 2:
                    obstacle = new Sand(x, y, width, height);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + obstacleType);
            }
            obstacles.add(obstacle); //add obstacles to the arraylist
        }
    }

    public void updateCourseState() {
        physicsEngine.handleCollisions(ball, obstacles);
        physicsEngine.applyMovement(ball, obstacles); // Update the ball's position
        keepBallWithinBounds();
    }

    private void keepBallWithinBounds() {
        if (ball.getPositionX() - Ball.RADIUS < 0) {
            ball.setPositionX(Ball.RADIUS);
        } else if (ball.getPositionX() + Ball.RADIUS > COURSE_LENGTH) {
            ball.setPositionX(COURSE_LENGTH - Ball.RADIUS);
        }

        /*if (ball.getPositionY() - Ball.RADIUS < 0) {
            ball.setPositionY(Ball.RADIUS);
        } else if (ball.getPositionY() + Ball.RADIUS > CANVAS_SIZE) {
            ball.setPositionY(CANVAS_SIZE - Ball.RADIUS);
        }*/
    }

    public boolean isHoleCompleted() {
        double distance = Math.sqrt(
                Math.pow(ball.getPositionX() - currentHole.getPositionX(), 2) +
                        Math.pow(ball.getPositionY() - currentHole.getPositionY(), 2)
        );
        return distance <= currentHole.RADIUS;
    }

    public void updateTrajectory() {
        float angle = club.getAngle();
        float power = club.getPower();
        // Calculate the initial velocity based on the angle and power
        float velocityX = (float) Math.cos(Math.toRadians(angle)) * power;
        float velocityY = (float) -Math.sin(Math.toRadians(angle)) * power;

        // Reset and calculate the trajectory points
        trajectoryPoints.clear();
        float simulationX = ball.getPositionX();
        float simulationY = ball.getPositionY();
        float gravity = 0.98f; // Gravity effect

        // Simulation loop to calculate trajectory points
        for (int i = 0; i < 20; i++) {
            simulationX += velocityX;
            simulationY += velocityY;
            velocityY += gravity; // Apply gravity effect

            trajectoryPoints.add(new Point((int)simulationX, (int)simulationY));
        }
    }

    public void clearTrajectory() {
        trajectoryPoints.clear();
    }

    public void generateNewHole() {
        placeHoleRandomly(); // Generate a new hole randomly
    }

    // Getters and setters
    public Ball getBall() {
        return ball;
    }

    public Club getClub() {
        return club;
    }
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    public Hole getCurrentHole() {
        return currentHole;
    }
    public boolean hasTrajectory() {
        return !trajectoryPoints.isEmpty();
    }

    // Getter for trajectory points
    public List<Point> getTrajectoryPoints() {
        return trajectoryPoints;
    }

    // New method to get the total width (length) of the golf course

    public int getHeight() {
        return CANVAS_SIZE;
    }
    public int getWidth() {
        return COURSE_LENGTH;
    }

    public Player getPlayer() {
        return this.player;
    }

    // Used to set all components of the game when loading game state
    public void setBallPosition(float x, float y) {
        ball.setPositionX(x);
        ball.setPositionY(y);
    }

    public void addObstacleFromString(String obstacleData) {
        String[] data = obstacleData.split(",");
        if (data.length != 5) {
            throw new IllegalArgumentException("Invalid obstacle format");
        }
        // Extract obstacle data from the string
        String type = data[0];
        float x = Float.parseFloat(data[1]);
        float y = Float.parseFloat(data[2]);
        int width = Integer.parseInt(data[3]);
        int height = Integer.parseInt(data[4]);

        Obstacle obstacle = null;
        switch (type) {
            case "Tree":
                obstacle = new Tree(x, y, width, height);
                break;
            case "Sand":
                obstacle = new Sand(x, y, width, height);
                break;
            case "Water":
                obstacle = new Water(x, y, width, height);
                break;
            default:
                break;
        }
        if (obstacle != null) {
            obstacles.add(obstacle);
        }
    }

    public List<String> serializeObstacles() {
        List<String> serializedObstacles = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            serializedObstacles.add(obstacle.toString());
        }
        return serializedObstacles;
    }

    // Method to load obstacles from a list of serialized strings
    public void loadObstacles(List<String> obstacleData) {
        obstacles.clear();
        for (String data : obstacleData) {
            addObstacleFromString(data);
        }
    }

    public void setCurrentHole(float x, float y) {
        this.currentHole = new Hole(x, y);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getGroundLevel() {
        return this.groundLevel;
    }
}
