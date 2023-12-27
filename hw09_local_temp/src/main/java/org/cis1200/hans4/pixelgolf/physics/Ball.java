package org.cis1200.hans4.pixelgolf.physics;

public class Ball {
    private float positionX, positionY;
    private float velocityX, velocityY;
    private float gravity = 0.98f; // Gravitational effect
    private float horizontalDeceleration = 0.5f; // Deceleration affecting horizontal movement
    public static final int RADIUS = 10;
    private final float groundLevel; // The y-coordinate of the ground level

    public Ball(float startX, float startY, float groundLevel) {
        this.positionX = startX;
        this.positionY = startY - RADIUS;
        this.velocityX = 0;
        this.velocityY = 0;
        this.groundLevel = groundLevel;
    }

    public void move() {

        positionX += velocityX;
        positionY += velocityY;

        velocityY += gravity;
        applyHorizontalDeceleration();
        checkBoundaryConditions();
    }

    private void applyHorizontalDeceleration() {
        if (Math.abs(velocityX) <= horizontalDeceleration) {
            velocityX = 0;
            return;
        }
        velocityX -= Math.signum(velocityX) * horizontalDeceleration;
    }

    private void checkBoundaryConditions() {
        if (positionY >= groundLevel - Ball.RADIUS) {
            positionY = groundLevel - Ball.RADIUS; // Set positionY to groundLevel
            velocityY = 0; // Stop the ball's vertical movement
        }
    }
    public void shoot(float power, float angle) {
        angle = (float) Math.toRadians(angle);
        velocityX = (float) Math.cos(angle) * power;
        velocityY = (float) -Math.sin(angle) * power;
    }


    // Reset the ball's position
    public void resetPosition() {
        positionX = 0;
        positionY = groundLevel - RADIUS; // Position the ball on the ground
        velocityX = 0;
        velocityY = 0;
    }

    // Elastic collision
    public void deflect(Vector2D normal) {
        Vector2D velocity = new Vector2D(velocityX,velocityY); //gets velocity before collision
        Vector2D reflectedvelocity = velocity.subtract(normal.multiply(2 * velocity.dot(normal)));
        //newv = v-2(n*v)*n

        velocityX = reflectedvelocity.getX();
        velocityY = reflectedvelocity.getY();
    }

    public boolean isMoving() {
        // A small threshold is used to account for very slow movements
        final float threshold = 0.01f;
        return Math.abs(velocityX) > threshold || Math.abs(velocityY) > threshold;
    }

    // Getters and setters
    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setPositionX(float x) {
        this.positionX = x;
    }

    public void setPositionY(float y) {
        this.positionY = y;
    }

    public static int getRadius() {
        return RADIUS;
    }
    public float getDeceleration() {
        return this.horizontalDeceleration;
    }

    public void setDeceleration(float newDeceleration) {
        this.horizontalDeceleration = newDeceleration;
    }

    // Additional methods as needed
}
