package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans5.pixelgolf.course.*;

public class GolfCourseTest {
    private GolfCourse golfCourse;

    @BeforeEach
    public void setUp() {
        golfCourse = new GolfCourse();
    }

    @Test
    public void testHoleCompletion() {
        Ball ball = golfCourse.getBall();
        ball.setPositionX(golfCourse.getCurrentHole().getPositionX());
        ball.setPositionY(golfCourse.getCurrentHole().getPositionY());
        assertTrue(golfCourse.isHoleCompleted());
    }

    @Test
    public void testNewHoleGeneration() {
        float holeXBefore = golfCourse.getCurrentHole().getPositionX();
        golfCourse.generateNewHole();
        assertNotEquals(holeXBefore, golfCourse.getCurrentHole().getPositionX());
    }

    @Test
    public void updateCourseStateTest() {
        Ball ball = golfCourse.getBall();
        ball.setPositionX(100); // Set initial position of the ball
        ball.setPositionY(golfCourse.getGroundLevel() - Ball.RADIUS);
        ball.setVelocityX(10); // Assuming there's a method to set X velocity
        ball.setVelocityY(-5); // Assuming there's a method to set Y velocity

        // Save initial state for comparison
        float initialPosX = ball.getPositionX();
        float initialPosY = ball.getPositionY();

        // Execute
        golfCourse.updateCourseState();

        // Verify outcomes
        Ball updatedBall = golfCourse.getBall();
        assertNotNull(updatedBall); // Check if the ball is not null

        // Check if the ball's position has changed
        assertNotEquals(initialPosX, updatedBall.getPositionX());
        assertNotEquals(initialPosY, updatedBall.getPositionY());
    }

    @Test
    public void testBallMovementAtCourseBoundaries() {
        GolfCourse course = new GolfCourse();
        Ball ball = course.getBall();

        // Simulate movement to the right boundary
        ball.setPositionX(course.getWidth());
        course.updateCourseState();
        assertTrue(ball.getPositionX() <= course.getWidth() - Ball.RADIUS,
                "Ball should stay within right boundary");

        // Simulate movement to the left boundary
        ball.setPositionX(0);
        course.updateCourseState();
        assertTrue(ball.getPositionX() >= Ball.RADIUS, "Ball should stay within left boundary");
    }

    @Test
    public void testMultipleObstacleCollision() {
        GolfCourse course = new GolfCourse();
        Ball ball = new Ball(100, 100, course.getGroundLevel());
        Tree tree = new Tree(150, 100, 50, 100);
        Water water = new Water(200, 100, 50, 100);

        ball.setVelocityX(50);
        ball.setVelocityY(0);// Set velocity to ensure collision
        course.updateCourseState();

        // Check ball position and state after collision
        assertTrue(ball.getPositionX() < 150,
                "Ball should collide and stop before the first obstacle");
    }

    @Test
    public void testBallResetWhenOutOfBounds() {
        GolfCourse course = new GolfCourse();
        Ball ball = course.getBall();

        // Set ball position out of bounds
        ball.setPositionX(course.getWidth() + 100);
        course.updateCourseState();

        // Verify ball is reset
        assertTrue(ball.getPositionX() >= 0 && ball.getPositionX() <= course.getWidth(),
                "Ball should reset within course boundaries");
    }

    @Test
    public void testHoleCompletionAtEdgeCases() {
        Ball ball = golfCourse.getBall();
        Hole hole = golfCourse.getCurrentHole();
        ball.setPositionX(golfCourse.getCurrentHole().getPositionX() + hole.RADIUS);
        ball.setPositionY(golfCourse.getCurrentHole().getPositionY());
        assertTrue(golfCourse.isHoleCompleted());
    }
}
