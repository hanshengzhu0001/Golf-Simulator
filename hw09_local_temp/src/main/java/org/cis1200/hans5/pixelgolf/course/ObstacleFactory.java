package org.cis1200.hans5.pixelgolf.course;

public class ObstacleFactory {

    // Factory method to create obstacles
    public static Obstacle createObstacle(String type, float x, float y, int width, int height) {
        switch (type) {
            case "Tree":
                return new Tree(x, y, width, height);
            case "Water":
                return new Water(x, y, width, height);
            case "Sand":
                return new Sand(x, y, width, height);
            default:
                throw new IllegalArgumentException("Unknown obstacle type: " + type);
        }
    }
}