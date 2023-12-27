package org.cis1200.hans2.pixelgolf.ui;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Font;

public class CircularSlider extends JComponent {
    private double angle = 0; // Angle in degrees
    private static final int DIAMETER = 100; // Diameter of the slider
    private static final int COMPASS_DIAMETER = 120; // Diameter of the outer compass circle

    public CircularSlider() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateAngle(e.getX(), e.getY());
            }
        });
    }

    private void updateAngle(int mouseX, int mouseY) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double deltaX = mouseX - centerX;
        double deltaY = centerY - mouseY; // Invert y-axis for graphical coordinates

        // Calculate the angle in radians from the horizontal axis (right)
        double angleRadians = Math.atan2(deltaY, deltaX);

        // Convert angle to degrees and adjust for anti-clockwise direction
        angle = Math.toDegrees(angleRadians);
        angle = (angle + 360) % 360; // Normalize to 0-360 range

        // Adjust the angle to start from the right and move anti-clockwise
        angle = (angle) % 360;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw outer compass circle and circular track
        int x = (getWidth() - COMPASS_DIAMETER) / 2;
        int y = (getHeight() - COMPASS_DIAMETER) / 2;
        g2d.drawOval(x, y, COMPASS_DIAMETER, COMPASS_DIAMETER);
        g2d.drawOval(x + 10, y + 10, DIAMETER, DIAMETER);

        // Calculate the needle's end position
        double radianAngle = Math.toRadians(angle);
        int lineX = (int) (x + COMPASS_DIAMETER / 2 + DIAMETER / 2 * Math.cos(radianAngle));
        int lineY = (int) (y + COMPASS_DIAMETER / 2 - DIAMETER / 2 * Math.sin(radianAngle));

        // Draw indicator line for the current angle
        g2d.setColor(Color.RED);
        g2d.draw(new Line2D.Double(x + COMPASS_DIAMETER / 2,
                y + COMPASS_DIAMETER / 2, lineX, lineY));

        // Draw compass directions and angle labels
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("N", getWidth() / 2 - 5, y);
        g2d.drawString("S", getWidth() / 2 - 5, y + COMPASS_DIAMETER);
        g2d.drawString("E", x + COMPASS_DIAMETER, getHeight() / 2 + 5);
        g2d.drawString("W", x - 15, getHeight() / 2 + 5);

        // Display the current angle as text
        g2d.drawString(String.format("%.0f°", angle), getWidth() / 2 - 15,
                y + COMPASS_DIAMETER / 2 - 15);
    }

    public double getAngle() {
        return angle;
    }
}