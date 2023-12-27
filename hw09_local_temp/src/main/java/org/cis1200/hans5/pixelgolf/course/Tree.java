package org.cis1200.hans5.pixelgolf.course;
import org.cis1200.hans4.pixelgolf.physics.Ball;
import org.cis1200.hans4.pixelgolf.physics.Vector2D;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tree extends Obstacle {
    public Tree(float x, float y, int width, int height) {
        super(x,y,width,height);
    }

    @Override
    public void draw(Graphics2D g2d, BufferedImage treeImg, int groundY) {
        int obstacleX = (int)this.getPositionX() - this.getWidth() / 2;
        int obstacleY = groundY - this.getHeight();
        g2d.drawImage(treeImg, obstacleX, obstacleY,
                this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void handleCollisions(Ball ball, Vector2D collisionNormal) {
        ball.deflect(collisionNormal);
    }

    @Override
    public String toString() {
        return "Tree," + getPositionX() + "," + getPositionY()
                + "," + getWidth() + "," + getHeight();
    }

}
