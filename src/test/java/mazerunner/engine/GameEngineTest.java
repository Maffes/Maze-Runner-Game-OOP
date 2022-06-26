package mazerunner.engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    GameEngine engine;

    @BeforeEach
    public void setUpGame() {
        engine = new GameEngine();
        engine.m = new Map();
        engine.m.setEngine(engine);
        engine.m.generateMap();
        engine.generateGame();
    }

    @Test
    void testToString() {
        String expectedValue;
        engine = new GameEngine();
        engine.p.setGold(2);
        engine.p.setStamina(10);
        expectedValue = "Move to start." + "\nGold: 2" + "\nStamina: 10";
        assertEquals(expectedValue, engine.toString());

        engine.p.setGold(4);
        engine.p.setStamina(9);
        engine.endGameWin();
        expectedValue = "You have won!" + "\nGold: 4" + "\nStamina: 9";
        assertEquals(expectedValue, engine.toString());
    }


    @Test
    void setDifficulty() {
        // If no difficulty is set, it should be 5
        assertEquals(5, engine.d);

        // Define a Scanner to get value from the given string
        ByteArrayInputStream in = new ByteArrayInputStream("9".getBytes());
        engine.input = new Scanner(in);
        engine.setDifficulty();
        assertEquals(9, engine.d);
    }



    @Test
    void textGame() {
        //Checks if player has moved up
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        engine.input = new Scanner(in);
        engine.p.setStamina(1);
        engine.textGame();
        assertEquals(8, engine.m.getPlayerX());

        //Checks if player has moved right
        GameEngine engine2 = new GameEngine();
        engine2.m = new Map();
        engine2.m.setEngine(engine2);
        engine2.m.generateMap();
        engine2.generateGame();
        ByteArrayInputStream in4 = new ByteArrayInputStream("1".getBytes());
        engine2.input = new Scanner(in4);
        engine2.p.setStamina(1);
        engine2.textGame();
        assertEquals(1, engine2.m.getPlayerY());

    }

    @Test
    void updateGame() {
        //check stamina is removed when on empty tile
        engine.leaveTile();
        engine.updateGame();
        assertEquals(11 , engine.p.getStamina());
    }

    @Test
    void endGame() {
        //checks if lost by stamina
        engine.p.setStamina(0);
        engine.endGame();
        assertEquals("Game over: You have no stamina.", engine.action);

        //checks if game won
        GameEngine engine = new GameEngine();
        engine.gameStatus = 1;
        engine.endGame();
        assertEquals("You have won!", engine.action);


        //checks if lost to trap
        GameEngine engine2 = new GameEngine();
        engine2.gameStatus = 2;
        engine2.endGame();
        assertEquals("Game over: You have no gold left.", engine2.action);

    }

}