package org.cis1200;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.cis1200.hans1.pixelgolf.Player;

public class PlayerTest {
    @Test
    public void testScoreIncrementAfterHole() {
        Player player = new Player();
        player.incrementScore(1);
        assertEquals(1, player.getScore());
    }

    @Test
    public void testPowerAndAngleAdjustment() {
        Player player = new Player();
        player.adjustPower(20);
        player.adjustAngle(45);
        assertEquals(20, player.getPower());
        assertEquals(45, player.getAngle());
    }


}
