package org.cis1200;

import javax.swing.*;
import org.cis1200.hans1.pixelgolf.BigGame;

public class Game {
    /**
     * Main method run to start and run the game. Initializes the runnable game
     * class of your choosing and runs it. IMPORTANT: Do NOT delete! You MUST
     * include a main method in your final submission.
     */
    public static void main(String[] args) {
        // Set the game you want to run here
        Runnable game = new BigGame();

        SwingUtilities.invokeLater(game);
    }
}