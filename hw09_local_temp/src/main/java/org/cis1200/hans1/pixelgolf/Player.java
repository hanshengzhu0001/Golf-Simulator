package org.cis1200.hans1.pixelgolf;

public class Player {
    private int score;
    private double power;
    private double angle;

    public Player() {
        this.score = 0;
        this.power = 50;
        this.angle = 0;
    }

    public void incrementScore(int strokes) {
        score += strokes;
    }

    public void nextHole() {
        power = 50;
        angle = 0;
    }

    public void adjustPower(double newPower) {
        this.power = newPower;
    }

    public void adjustAngle(double newAngle) {
        this.angle = newAngle;
    }

    // Getters and setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPower() {
        return power;
    }

    public double getAngle() {
        return angle;
    }
}

