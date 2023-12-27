package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.Vector2D;
import org.cis1200.hans5.pixelgolf.course.*;

public class ObstacleTest {
    private GolfCourse golfCourse;

    private Ball ball;

    @BeforeEach
    public void setUp() {
        golfCourse = new GolfCourse();
        ball = new Ball(0,0,golfCourse.getGroundLevel());
    }

    @Test
    public void testObstacleInitialization() {
        Tree tree = new Tree(100, golfCourse.getGroundLevel(), 50, 100);
        assertEquals(100, tree.getPositionX());
        assertEquals(golfCourse.getGroundLevel(), tree.getPositionY());
        assertEquals(50, tree.getWidth());
        assertEquals(100, tree.getHeight());

        Water water = new Water(100, golfCourse.getGroundLevel(), 50, 100);
        assertEquals(100, water.getPositionX());
        assertEquals(golfCourse.getGroundLevel(), water.getPositionY());
        assertEquals(50, water.getWidth());
        assertEquals(100, water.getHeight());

        Sand sand = new Sand(100, golfCourse.getGroundLevel(), 50, 100);
        assertEquals(100, sand.getPositionX());
        assertEquals(golfCourse.getGroundLevel(), sand.getPositionY());
        assertEquals(50, sand.getWidth());
        assertEquals(100, sand.getHeight());
    }

    @Test
    public void testTreeCollisionResponse() {
        Tree tree = new Tree(100, golfCourse.getGroundLevel(), 50, 100);
        ball.setPositionX(95);
        ball.setPositionY(golfCourse.getGroundLevel() - Ball.RADIUS);
        ball.shoot(10, 0); // Initial shoot in a certain direction

        Vector2D initialVelocity = new Vector2D(ball.getVelocityX(), ball.getVelocityY());

        tree.handleCollisions(ball, new Vector2D(1, 0));

        Vector2D postCollisionVelocity = new Vector2D(ball.getVelocityX(), ball.getVelocityY());

        assertNotEquals(initialVelocity.getX(), postCollisionVelocity.getX());
        // You can also check the Y component if your deflection affects it
    }


    @Test
    public void testWaterCollisionResponse() {
        Water water = new Water(100, golfCourse.getGroundLevel(), 50, 100);
        ball.setPositionX(95);
        ball.setPositionY(golfCourse.getGroundLevel() - Ball.RADIUS);
        ball.shoot(10, 0); // Shoot the ball towards the water

        water.handleCollisions(ball, new Vector2D(1, 0));

        // Check if the ball's position is reset to (0, 0, golfCourse.getGroundLevel())
        assertEquals(0, ball.getPositionX());
        assertEquals(golfCourse.getGroundLevel() - ball.RADIUS, ball.getPositionY());
        assertFalse(ball.isMoving()); //
    }

    @Test
    public void testSandCollisionResponse() {
        Water water = new Water(100, golfCourse.getGroundLevel(), 50, 100);
        ball.setPositionX(95);
        ball.setPositionY(golfCourse.getGroundLevel() - Ball.RADIUS);
        ball.shoot(10, 0);

        Vector2D initialVelocity = new Vector2D(ball.getVelocityX(), ball.getVelocityY());

        water.handleCollisions(ball, new Vector2D(1, 0));

        Vector2D postCollisionVelocity = new Vector2D(ball.getVelocityX(), ball.getVelocityY());

        assertNotEquals(initialVelocity.getX(), postCollisionVelocity.getX());
    }
}
