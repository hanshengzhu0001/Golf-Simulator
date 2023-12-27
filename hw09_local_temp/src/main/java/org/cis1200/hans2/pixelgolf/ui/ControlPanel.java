package org.cis1200.hans2.pixelgolf.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.cis1200.hans1.pixelgolf.GameController;
import org.cis1200.hans3.pixelgolf.utils.GameSaver;
import org.cis1200.hans3.pixelgolf.utils.GameState;

public class ControlPanel extends JPanel {
    private GameController gameController;
    private JButton shootButton;
    private JSlider powerSlider;
    private CircularSlider angleSlider;
    private GameSaver gameSaver;

    private JButton loadGameButton;
    private JButton saveGameButton;

    public ControlPanel(GameController controller) {
        this.gameController = controller;
        this.gameSaver = new GameSaver();

        loadGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Game");

        setLayout(new BorderLayout()); // Use BorderLayout for better layout control
        setupToolbar(); // Set up the toolbar with buttons
        setupControls(); // Set up other controls
        registerControlListeners(); // Register listeners
    }

    private void setupToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(loadGameButton);
        toolBar.add(saveGameButton);

        add(toolBar, BorderLayout.CENTER);
    }

    private void setupControls() {
        // Panel for the shoot button and power slider
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shootButton = new JButton("Shoot");
        buttonPanel.add(shootButton);

        // Adjusted power slider range to 0-100
        powerSlider = new JSlider(0, 100);
        powerSlider.setMajorTickSpacing(10);
        powerSlider.setPaintTicks(true);
        powerSlider.setPaintLabels(true);
        buttonPanel.add(powerSlider);

        add(buttonPanel, BorderLayout.WEST); // Add button panel to the west

        // Initialize the circular angle slider
        angleSlider = new CircularSlider();
        angleSlider.setPreferredSize(new Dimension(120, 120));
        add(angleSlider, BorderLayout.EAST); // Add angle slider to the east
    }

    private void registerControlListeners() {
        shootButton.addActionListener(e ->
                gameController.handlePlayerAction(GameController.Action.SHOOT, 0));

        powerSlider.addChangeListener(e -> {
            float power = powerSlider.getValue();
            gameController.handlePlayerAction(GameController.Action.ADJUST_POWER, power);
        });

        angleSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double angle = angleSlider.getAngle();
                gameController.handlePlayerAction(GameController.Action.ADJUST_ANGLE, angle);
            }
        });

        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameController.saveCurrentGameState();
                    JOptionPane.showMessageDialog(null,
                            "Game saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to save game: "
                            + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GameState loadedState = gameSaver.loadGameState();
                    gameController.loadGame(loadedState);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to load game: "
                            + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

