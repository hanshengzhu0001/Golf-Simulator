package org.cis1200.hans5.pixelgolf.course;

import org.cis1200.hans4.pixelgolf.physics.Ball;
public class Club {
    private float power;
    private float angle;

    public Club() {
        this.power = 50; // Default power, adjust as needed
        this.angle = 0; // Default angle, adjust as needed
    }

    public void swing(Ball ball) {
        // The swing method will set the ball's velocity based on the club's power and angle
        ball.shoot(power, angle);
    }

    // Getters and setters
    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
