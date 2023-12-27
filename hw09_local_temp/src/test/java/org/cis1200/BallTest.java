package org.cis1200;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans4.pixelgolf.physics.Ball;

public class BallTest {
    private Ball ball;
    private final float groundLevel = 100.0f; // Example ground level

    @BeforeEach
    public void setUp() {
        ball = new Ball(0, groundLevel, groundLevel);
    }

    @Test
    public void testBallInitialization() {
        assertEquals(0, ball.getPositionX());
        assertEquals(groundLevel - Ball.RADIUS, ball.getPositionY());
        assertEquals(0, ball.getVelocityX());
        assertEquals(0, ball.getVelocityY());
    }

    @Test
    public void testBallInitialVelocity() {
        float power = 10;
        float angle = 45;
        ball.shoot(power, angle);

        float expectedVelocityX = (float) (power * Math.cos(Math.toRadians(angle)));
        float expectedVelocityY = (float) (power * Math.sin(Math.toRadians(angle)));

        assertEquals(expectedVelocityX, ball.getVelocityX());
        assertEquals(-expectedVelocityY, ball.getVelocityY()); // Negative due to upward direction
    }

    @Test
    public void testBallMovementOverTime() {
        ball.shoot(10, 45);
        ball.move();

        assertTrue(ball.getPositionX() > 0);
        assertTrue(ball.getPositionY() < groundLevel);
    }

    //Edge case
    @Test
    public void testBallStopsAtLowVelocity() {
        ball.setVelocityX(0.01f);
        ball.setVelocityX(0);
        ball.move();
        assertFalse(ball.isMoving());
    }
}
