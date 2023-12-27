package org.cis1200.hans4.pixelgolf.physics;

public class Vector2D {
    private float x,y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public float dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void normalize() {
        float magnitude = (float) Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

        if (magnitude != 0) {
            this.x = x / magnitude;
            this.y = y / magnitude;
        }
    }
}