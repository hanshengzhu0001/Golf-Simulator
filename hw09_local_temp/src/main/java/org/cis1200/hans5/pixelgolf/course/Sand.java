package org.cis1200.hans5.pixelgolf.course;

import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sand extends Obstacle {
    private static final float SAND_DECELERATION = 3.0f; // Increased deceleration in sand
    public Sand(float x, float y, int width, int height) {
        super(x,y,width,height);
    }

    @Override
    public void draw(Graphics2D g2d, BufferedImage sandImg, int groundY) {
        int obstacleX = (int)this.getPositionX() - this.getWidth() / 2;
        int obstacleY = groundY - this.getHeight() / 2;
        g2d.drawImage(sandImg, obstacleX, obstacleY, this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void handleCollisions(Ball ball, Vector2D collisionNormal) {
        ball.setDeceleration(SAND_DECELERATION);
    }

    @Override
    public String toString() {
        return "Sand," + getPositionX() + "," + getPositionY() + ","
                + getWidth() + "," + getHeight();
    }
}
