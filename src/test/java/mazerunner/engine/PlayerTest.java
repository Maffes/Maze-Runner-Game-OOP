package mazerunner.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player p = new Player();

    @Test
    void resetPlayer() {
        assertEquals(12, p.getStamina());
        assertEquals(0, p.getGold());
    }

    @Test
    void decrementStamina() {
        p.decrementStamina();
        assertEquals(11, p.getStamina());
    }

    @Test
    void decrementGold() {
        p.decrementGold();
        assertEquals(-1, p.getGold());
    }

    @Test
    void incrementGold() {
        p.incrementGold();
        assertEquals(1, p.getGold());
    }
}