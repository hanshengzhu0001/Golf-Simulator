package org.cis1200.hans5.pixelgolf.course;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Water extends Obstacle {
    public Water(float x, float y, int width, int height) {
        super(x,y,width,height);
    }

    @Override
    public void draw(Graphics2D g2d, BufferedImage waterImg, int groundY) {
        int obstacleX = (int)this.getPositionX() - this.getWidth() / 2;
        int obstacleY = groundY - this.getHeight() / 2;
        g2d.drawImage(waterImg, obstacleX, obstacleY, this.getWidth(), this.getHeight(), null);
    }
    @Override
    public void handleCollisions(Ball ball, Vector2D collisionNormal) {
        ball.resetPosition();
    }

    @Override
    public String toString() {
        return "Water," + getPositionX() + "," + getPositionY()
                + "," + getWidth() + "," + getHeight();
    }
}
