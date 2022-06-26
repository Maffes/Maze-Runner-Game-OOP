package mazerunner.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {


    @BeforeEach
    public void setUpGame() {
        engine = new GameEngine();
        engine.m = new Map();
        engine.m.setEngine(engine);
        engine.m.generateMap();
        engine.generateGame();
    }

    GameEngine engine = new GameEngine();
    Map m = new Map();

    @Test
    void generateMap() {
        m.generateMap();
        assertEquals(Tile.Empty, m.board[2][2]);
    }


    @Test
    void moveUp() {
        engine.m.moveUp();
        assertEquals(8, engine.m.getPlayerX());
    }

    @Test
    void moveDown() {
        engine.m.moveDown();
        assertEquals("You can't move there!", engine.action );
    }

    @Test
    void moveRight() {
        engine.m.moveRight();
        assertEquals(1, engine.m.getPlayerY());
    }

    @Test
    void moveLeft() {
        engine.m.moveLeft();
        assertEquals("You can't move there!", engine.action );
    }



}