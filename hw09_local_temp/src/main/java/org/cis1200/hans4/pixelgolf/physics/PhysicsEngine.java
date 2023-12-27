package org.cis1200.hans4.pixelgolf.physics;

import org.cis1200.hans5.pixelgolf.course.Obstacle;
import org.cis1200.hans5.pixelgolf.course.Sand;
import org.cis1200.hans5.pixelgolf.course.Tree;
import java.util.List;

public class PhysicsEngine {

    private static final float NORMAL_DECELERATION = 0.5f; // Normal deceleration rate

    public void applyMovement(Ball ball, List<Obstacle> obstacles) {
        ball.move();
        resetDecelerationIfOutOfSand(ball, obstacles);
    }

    public void handleCollisions(Ball ball, List<Obstacle> obstacles) {

        for (Obstacle obstacle : obstacles) {
            if (isCollisionDetected(ball, obstacle)) {
                Vector2D collisionNormal = obstacle.getCollisionNormal(ball);
                obstacle.handleCollisions(ball,collisionNormal);
            }
        }
    }

    public boolean isCollisionDetected(Ball ball, Obstacle obstacle) {
        // Ball's bounding box
        float ballLeft = ball.getPositionX() - Ball.RADIUS;
        float ballRight = ball.getPositionX() + Ball.RADIUS;
        float ballBottom = ball.getPositionY() + Ball.RADIUS;

        float obstacleTop;

        // Obstacle's bounding box
        float obstacleLeft = obstacle.getPositionX() - obstacle.getWidth() / 2;
        float obstacleRight = obstacle.getPositionX() + obstacle.getWidth() / 2;

        if (obstacle instanceof Tree) {
            obstacleTop = obstacle.getPositionY() - obstacle.getHeight();
        } else {
            obstacleTop = obstacle.getPositionY() - obstacle.getHeight() / 2;
        }

        // Check if the bounding boxes overlap
        return ballRight > (obstacleLeft - 10) &&
                ballLeft < (obstacleRight + 10) &&
                ballBottom > (obstacleTop - 10);
    }

    private void resetDecelerationIfOutOfSand(Ball ball, List<Obstacle> obstacles) {
        boolean isInSand = false;
        for (Obstacle obstacle : obstacles) {
            if (isCollisionDetected(ball, obstacle) && (obstacle instanceof Sand)) {
                isInSand = true;
                break;
            }
        }

        if (!isInSand) {
            ball.setDeceleration(NORMAL_DECELERATION);
        }
    }
}