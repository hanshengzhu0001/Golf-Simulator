package org.cis1200;

import java.util.*;

import org.cis1200.hans5.pixelgolf.course.GolfCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.PhysicsEngine;
import org.cis1200.hans5.pixelgolf.course.Obstacle;
import org.cis1200.hans5.pixelgolf.course.ObstacleFactory;

public class PhysicsEngineTest {
    private PhysicsEngine physicsEngine;
    private GolfCourse golfCourse;

    @BeforeEach
    public void setUp() {
        golfCourse = new GolfCourse();
        physicsEngine = new PhysicsEngine();
    }

    @Test
    public void testBallPhysicsAfterMovement() {
        Ball ball = new Ball(0, 0, golfCourse.getGroundLevel());
        ball.shoot(10, 45); // Shoot the ball with some power and angle

        float initialX = ball.getPositionX();
        float initialY = ball.getPositionY();

        physicsEngine.applyMovement(ball, new ArrayList<>()); // Apply movement without obstacles

        // Check if the ball has moved correctly
        assertTrue(ball.getPositionX() > initialX);
        assertTrue(ball.getPositionY() < initialY); // Y should decrease as ball goes up
    }

    @Test
    public void testCollisionDetection() {
        Ball ball = new Ball(0, 0, golfCourse.getGroundLevel());
        Obstacle tree = ObstacleFactory.createObstacle(
                "Tree", 50, golfCourse.getGroundLevel(), 10, 20);
        List<Obstacle> obstacles = Arrays.asList(tree);

        // Position ball close to the tree
        ball.setPositionX(45);
        ball.setPositionY(golfCourse.getGroundLevel() - Ball.RADIUS);

        physicsEngine.handleCollisions(ball, obstacles);

        // Check for collision
        assertFalse(ball.isMoving()); // Assuming the tree stops the ball
    }
}
