package org.cis1200.hans2.pixelgolf.ui;

import javax.swing.*;
import java.awt.*;
import org.cis1200.hans1.pixelgolf.GameController;
import org.cis1200.hans5.pixelgolf.course.GolfCourse;

public class GameWindow extends JFrame {
    private CoursePanel coursePanel;
    private ScorePanel scorePanel;
    private ControlPanel controlPanel;
    private GameController gameController;
    private JLabel timerLabel;

    public GameWindow(GameController controller, GolfCourse course) {
        this.gameController = controller;

        coursePanel = new CoursePanel(course);
        scorePanel = new ScorePanel();
        controlPanel = new ControlPanel(controller);

        timerLabel = new JLabel("Time: 0:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        initializeWindow();
        updateScoreDisplay();
    }

    public void initializeWindow() {
        setTitle("Pixel Golf Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(coursePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(timerLabel, BorderLayout.WEST);

        JPanel scoreCornerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scoreCornerPanel.add(scorePanel);
        topPanel.add(scoreCornerPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        pack();
        setVisible(true);
    }

    public void refreshDisplay(int cameraX) {
        coursePanel.setCameraX(cameraX);
        coursePanel.repaint();
        updateScoreDisplay();
    }

    public void updateTimer(long elapsedMillis) {
        int seconds = (int) (elapsedMillis / 1000) % 60;
        int minutes = (int) (elapsedMillis / (1000 * 60)) % 60;
        timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
    }

    //gets the player's current score
    private void updateScoreDisplay() {
        int currentScore = gameController.getCurrentPlayerScore();
        scorePanel.updateScore(currentScore);
    }
}

