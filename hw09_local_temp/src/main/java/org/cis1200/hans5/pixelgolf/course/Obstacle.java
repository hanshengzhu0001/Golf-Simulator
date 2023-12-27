package org.cis1200.hans5.pixelgolf.course;

import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.Vector2D;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Obstacle {
    private float positionX, positionY; //center position
    private int width, height;

    public Obstacle(float x, float y, int width, int height) {
        this.positionX = x;
        this.positionY = y;
        this.width = width;
        this.height = height;
    }

    public abstract void handleCollisions(Ball ball, Vector2D collisionNormal);

    public abstract String toString();

    public abstract void draw(Graphics2D g2d, BufferedImage img, int groundY);

    public Vector2D getCollisionNormal(Ball ball) {
        float ballCenterX = ball.getPositionX();
        float ballCenterY = ball.getPositionY();

        // Calculate the tree's bounding box
        float treeLeftX = this.positionX - this.width / 2f;
        float treeRightX = this.positionX + this.width / 2f;
        float treeTopY = this.positionY - this.height;

        // Check for top collision
        if (ballCenterY < treeTopY && ballCenterX > treeLeftX && ballCenterX < treeRightX) {
            return new Vector2D(0, -1); // Upwards normal for top collision
        }

        // Check for left or right side collision
        float closestX = Math.max(treeLeftX, Math.min(treeRightX, ballCenterX));
        if (ballCenterX < treeLeftX || ballCenterX > treeRightX) {
            float direction = (ballCenterX < treeLeftX) ? 1 : -1; // Right or left normal
            return new Vector2D(direction, 0);
        }

        // Default closest point calculation for other scenarios
        float closestY = Math.max(treeTopY, ballCenterY);
        Vector2D normal = new Vector2D(ballCenterX - closestX, ballCenterY - closestY);
        normal.normalize();

        return normal;
    }

    // Getters
    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getLeft() {
        return positionX;
    }

    public float getRight() {
        return positionX + width;
    }

    public float getBottom() {
        return positionY + height;
    }

    public float getTop() {
        return positionY - height;
    }
}

