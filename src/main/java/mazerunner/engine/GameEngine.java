package mazerunner.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine{
    public Map m;
    public Player p = new Player();
    public Scanner input = new Scanner(System.in);
    public int d = 5;
    public int gameStatus = 0;
    public String action = "Move to start.";
    private final static String fileName = "save.txt";


    /**
     * Main method which creates an engine object required resources.
     *
     * @param args
     */
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.m = new Map();
        engine.m.setEngine(engine);
        engine.setDifficulty();
        engine.m.generateMap();
        engine.generateGame();
        engine.textGame();
    }


    /**
     * Prompts input for text game difficulty and sets it to d.
     *
     * If input is out of range, the input is re-prompted.
     */
    public void setDifficulty() {
        System.out.println("Type Difficulty:");
        d = input.nextInt();
        if (d > 10) {
            System.out.println("Choose a number between 0-10.");
            setDifficulty();
        }
    }



    /**
     * Generates a game setting the game status and players position.
     *
     * Runs generateMap method passing through the game difficulty. Moreover, the player stats are reset in case of new game.
     */
    public void generateGame() {
        gameStatus = 0;
        m.setPlayerX(9);
        m.setPlayerY(0);
        m.tileGenerator(d);
        p.resetPlayer();
    }


    /**
     *
     * This method is used for handling the text based version of the game.
     *
     * Prints the map then checks if the game is still active then prompts a response to move the player in a direction
     *
     * Based on input a switch call the corresponding method to update the map.
     *
     * After, the stamina, gold and map are printed to the console.
     *
     * If game is no longer valid, the endGame method is called.
     *
     */
    public void textGame() {
        System.out.print(m);
        while (p.getStamina() > 0 && gameStatus == 0) {
            System.out.println("\n0 = Up | 1 = Right | 2 = Down | 3 = Left");
            int textController = input.nextInt();
            switch (textController) {
                case 0:
                    m.moveUp();
                    break;
                case 1:
                    m.moveRight();
                    break;
                case 2:
                    m.moveDown();
                    break;
                case 3:
                    m.moveLeft();
                    break;
            }
            System.out.println("Your Stamina is: " + p.getStamina());
            System.out.println("You have " + p.getGold() + " gold.\n");
            System.out.print(m);
        }
        endGame();
    }


    /**
     * Based on the tile type the player is positioned on, the corresponding altercation to the player stats are made.
     */
    public void updateGame() {
        if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.Empty) {
            p.decrementStamina();
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Player;
        } else if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.Gold) {
            p.incrementGold();
            p.decrementStamina();
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Player;
            System.out.println("Nice! You have collected 1 gold.");
        } else if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.Trap) {
            p.decrementGold();
            p.decrementStamina();
            System.out.println("Oh no! You are trapped.");
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.PlayerTrap;
            if (p.getGold() < 0) {
                gameStatus = 2;
            }
        } else if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.Apple) {
            p.setStamina(p.getStamina() + 3);
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Player;
            System.out.println("Yum! You have collected an apple and your stamina has increased.");
        } else if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.Exit) {
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Player;
            gameStatus = 1;
        }
    }



    /**
     * Checks if the Player is leaving a trap so it can be assigned back to the trap tile. Otherwise sets it too empty.
     */
    public void leaveTile() {
        if (m.board[m.getPlayerX()][m.getPlayerY()] == Tile.PlayerTrap) {
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Trap;
        } else {
            m.board[m.getPlayerX()][m.getPlayerY()] = Tile.Empty;
        }
    }


    /**
     * when the game has ended this method produces the correct end result.
     */
    public void endGame() {
        if (p.getStamina() == 0) {
            endGameStamina();
        } else if (gameStatus == 1) {
            endGameWin();
        } else if (gameStatus == 2) {
            endGameTrap();
        }
    }


    /**
     *  Prints out you have won with score and updates action variable
     */
    public void endGameWin() {
        System.out.printf("\nYou have won! Your score is: %d", p.getGold());
        action = "You have won!";
    }

    /**
     * Warns player they have lost and updates the action variable and gameStatus.
     */
    public void endGameStamina() {
        gameStatus = 1;
        System.out.println("\nGAME OVER: YOU HAVE RAN OUT OF STAMINA!");
        action = "Game over: You have no stamina.";
    }


    /**
     * Warns player they have lost and updates the action variable.
     */
    public void endGameTrap() {
        System.out.println("\nGAME OVER: YOU STOOD ON A TRAP AND HAD NO GOLD LEFT!");
        action = "Game over: You have no gold left.";
    }


    /**
     * toString to print the action, gold and stamina to the GUI.
     * @return
     */

    public String toString() {
        return action + "\nGold: " + p.getGold() + "\nStamina: " + p.getStamina();
    }



    /**
     * SaveGame method writes the players stats and positioning to a textfile. Moreover, saves the map as an array.
     */
    public void saveGame() {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(p.getStamina());
            writer.println(p.getGold());
            writer.println(m.getPlayerX());
            writer.println(m.getPlayerY());
            writer.print(Arrays.deepToString(m.board));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a text file including the players stats and positioning. Loads the map array then converts it to enum values.
     */
    public void loadGame() {
        try {
            gameStatus = 0;
            Scanner loader = new Scanner(new File(fileName));
            p.setStamina(loader.nextInt());
            p.setGold(loader.nextInt());
            m.setPlayerX(loader.nextInt());
            m.setPlayerY(loader.nextInt());
            action = "Saved game loaded.";
            String[][] board = new String[10][10];
            for (int i = 0; i < m.board.length; i++) {
                for (int j = 0; j < m.board[i].length; j++) {
                    board[i][j] = loader.next();
                    board[i][j] = board[i][j].replaceAll("[\\[\\],]", "");
                }
            }
            for (int i = 0; i < m.board.length; i++) {
                for (int j = 0; j < m.board[i].length; j++) {
                    m.board[i][j] = Tile.valueOf(board[i][j]);
                }
            }
            loader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}