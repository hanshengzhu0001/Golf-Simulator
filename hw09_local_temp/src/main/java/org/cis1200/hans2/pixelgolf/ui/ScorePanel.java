package org.cis1200.hans2.pixelgolf.ui;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score;

    public ScorePanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        add(scoreLabel, BorderLayout.NORTH);
    }

    public void updateScore(int newScore) {
        score = newScore;
        JLabel scoreLabel = (JLabel) getComponent(0);
        scoreLabel.setText("Score: " + score);
        revalidate();
        repaint();
    }
}

