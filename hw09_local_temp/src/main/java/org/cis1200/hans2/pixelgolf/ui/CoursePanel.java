package org.cis1200.hans2.pixelgolf.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.cis1200.hans1.pixelgolf.GameController;
import org.cis1200.hans4.pixelgolf.physics.*;
import org.cis1200.hans5.pixelgolf.course.*;


public class CoursePanel extends JPanel {
    private GolfCourse golfCourse;
    private int cameraX; // Camera's horizontal position
    private GameController gameController;
    private BufferedImage golfBallImg, holeImg, treeImg, waterImg, sandImg, clubImg;

    public CoursePanel(GolfCourse course) {
        this.golfCourse = course;
        setPreferredSize(new Dimension(800, 600)); // Example size, adjust as needed
        loadImages();
    }

    public static final String GOLF_FILE = "files/Golf.png";

    public static final String HOLE_FILE = "files/Hole.png";
    public static final String TREE_FILE = "files/Tree.png";
    public static final String WATER_FILE = "files/Water.png";
    public static final String SAND_FILE = "files/Sand.png";
    public static final String CLUB_FILE = "files/Club.png";
    private void loadImages() {
        try {
            golfBallImg = ImageIO.read(new File(GOLF_FILE));
            holeImg = ImageIO.read(new File(HOLE_FILE));
            treeImg = ImageIO.read(new File(TREE_FILE));
            waterImg = ImageIO.read(new File(WATER_FILE));
            sandImg = ImageIO.read(new File(SAND_FILE));
            clubImg = ImageIO.read(new File(CLUB_FILE));
        } catch (IOException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-cameraX, 0);
        drawCourseBackground(g2d);

        for (Obstacle obstacle : golfCourse.getObstacles()) {
            drawObstacle(g2d, obstacle);
        }

        drawHole(g2d, golfCourse.getCurrentHole());
        drawBall(g2d, golfCourse.getBall());

        if (golfCourse.getClub() != null) {
            drawClub(g2d, golfCourse.getBall(), golfCourse.getClub().getAngle());
            drawTrajectory(g2d, golfCourse.getTrajectoryPoints());
        }
    }

    private void drawCourseBackground(Graphics2D g2d) {
        int panelHeight = golfCourse.getHeight();
        int groundHeight = (int)(panelHeight * 0.2); // 20% of panel height for ground
        int skyHeight = panelHeight - groundHeight; // Remaining height for sky

        // Draw the ground
        g2d.setColor(new Color(0, 128, 0)); // Green for grass
        g2d.fillRect(0, skyHeight, golfCourse.getWidth(), groundHeight); // Draw the grass

        // Draw the sky
        g2d.setColor(new Color(135, 206, 235)); // Blue for sky
        g2d.fillRect(0, 0, golfCourse.getWidth(), skyHeight); // Draw the sky
    }

    private void drawObstacle(Graphics2D g2d, Obstacle obstacle) {
        int panelHeight = golfCourse.getHeight();
        int groundHeight = (int)(panelHeight * 0.2);
        int groundY = panelHeight - groundHeight;

        if (obstacle instanceof Tree) {
            ((Tree) obstacle).draw(g2d, treeImg, groundY);
        } else if (obstacle instanceof Water) {
            // Assuming Water class also has a draw method
            ((Water) obstacle).draw(g2d, waterImg, groundY);
        } else if (obstacle instanceof Sand) {
            // Assuming Sand class also has a draw method
            ((Sand) obstacle).draw(g2d, sandImg, groundY);
        }
    }

    private void drawHole(Graphics2D g2d, Hole hole) {
        int panelHeight = golfCourse.getHeight();
        int groundHeight = (int)(panelHeight * 0.2);
        int groundY = panelHeight - groundHeight; // Y-coordinate for the top of the ground

        int x = (int) hole.getPositionX() - Hole.RADIUS;
        int y = groundY - Hole.RADIUS; // Position center of the hole on the ground

        g2d.drawImage(holeImg, x, y, Hole.RADIUS * 2, Hole.RADIUS * 2, null);
    }


    private void drawBall(Graphics2D g2d, Ball ball) {
        int x = (int) ball.getPositionX() - Ball.RADIUS;
        int y = (int) ball.getPositionY() - Ball.RADIUS;

        g2d.drawImage(golfBallImg, x, y, Ball.RADIUS * 2, Ball.RADIUS * 2, null);
    }

    private void drawClub(Graphics2D g2d, Ball ball, float swingAngle) {
        boolean isClubVisible = shouldDisplayClub(ball);
        int panelHeight = golfCourse.getHeight();

        if (isClubVisible) {
            int desiredClubWidth = 80; // Adjust as needed
            int desiredClubHeight = 120; // Adjust as needed

            int ballX = (int) ball.getPositionX();
            int groundY = panelHeight - (int)(panelHeight * 0.2); // Ground level

            // Calculate the position for the club
            int clubX = ballX - desiredClubWidth / 2 - 10; // Positioned to the left of the ball
            int clubY = groundY - desiredClubHeight;

            // Draw the club at the rotated position
            g2d.drawImage(clubImg, clubX, clubY, desiredClubWidth, desiredClubHeight, null);

            // Reset swing angle once the club is no longer visible
            if (!isClubVisible) {
                golfCourse.getClub().setAngle(0);
            }
        }
    }

    private boolean shouldDisplayClub(Ball ball) {
        return !ball.isMoving();
    }

    private void drawTrajectory(Graphics2D g2d, List<Point> trajectoryPoints) {
        g2d.setColor(Color.WHITE); // Set color for trajectory

        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f,
                new float[]{9}, 0);
        g2d.setStroke(dashed);

        for (int i = 0; i < trajectoryPoints.size() - 1; i++) {
            Point start = trajectoryPoints.get(i);
            Point end = trajectoryPoints.get(i + 1);

            g2d.drawLine(start.x, start.y, end.x, end.y); // Draw line between points
        }
    }
}