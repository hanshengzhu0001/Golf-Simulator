package org.cis1200.hans5.pixelgolf.course;

public class Hole {
    private float positionX, positionY;
    public static final int RADIUS = 20; // Radius of the hole

    public Hole(float x, float y) {
        this.positionX = x;
        this.positionY = y;
    }

    // Getters
    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }
}

