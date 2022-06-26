package mazerunner.engine;

import java.util.Random;


/**
 * The Map class is used to create a map object.....
 */
public class Map {
    Random rand = new Random();
    public Tile[][] board;
    private static int playerX = 9;
    private static int playerY = 0;
    GameEngine engine;

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Creates a 2d array for the map.
     */
    public Map() {
        board = new Tile[10][10];
    }

    /**
     * Resets the map 2d array.
     */
    public void generateMap() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Tile.Empty;
            }
        }
    }

    /**
     * Sets the player positions before other tiles are generated to avoid conflict.
     *
     * For loop for each type of tile generated. d is passed down for difficulty and i is passed down to identify tile type.
     *
     * @param d
     */
    public void tileGenerator(int d) {
        System.out.println(d);
        board[9][0] = Tile.Player;
        for (int i = 0; i < 4; i++){
            generateItems(d, i);
        }
    }

    /**
     *
     * Switch is used to determin which tile type is to be generated. Furthermore, within each case TileAmount is calculated and set to create the right amount of tiles.
     *
     * A for loop is used to create each tile. The x and y positioning is generated between 0-9 then checks if that position is empty.
     *
     * If the position is empty it will assign the new tile else increments TileAmount by 1 to attempt again.
     *
     * @param d
     * @param type
     */
    public void generateItems(int d, int type) {
       int TileAmount = 0;
        switch (type) {
            case 0:
                TileAmount = 1;
                break;
            case 1:
                TileAmount = 10 - d;
                break;
            case 2:
                TileAmount = d;
                break;
            case 3:
                TileAmount = 5;
                break;
        }
        for (int i = 0; i < TileAmount; i++) {
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);
            if (board[x][y] == Tile.Empty){
                //Need to change Exit below to something that will populate based of type
                if (type == 0){
                    board[x][y] = Tile.Exit;
                }
                if (type == 1){
                    board[x][y] = Tile.Apple;
                }
                if (type == 2){
                    board[x][y] = Tile.Trap;
                }
                if (type == 3){
                    board[x][y] = Tile.Gold;
                }
            } else TileAmount++;
        }
    }


    /**
     * This method moves the player up.
     *
     * When called checks if the player is moving inside of the map otherwise warns player they can't move there.
     *
     * If the move is valid, removed the player from the previous tile move the player position then update the game with new positioning.
     *
     * After the player has moved, prints to console a message and updates the action variable.
     */
    public void moveUp() {
        if (playerX > 0) {
            engine.leaveTile();
            playerX--;
            engine.updateGame();
            System.out.println("You have moved up.");
            engine.action = "You have moved up.";
        }
        else {
            System.out.println("You can't move there!");
            engine.action = "You can't move there!";
        }
    }


    /**
     * This method moves the player down.
     *
     * When called checks if the player is moving inside of the map otherwise warns player they can't move there.
     *
     * If the move is valid, removed the player from the previous tile move the player position then update the game with new positioning.
     *
     * After the player has moved, prints to console a message and updates the action variable.
     */
        public void moveDown() {
        if (playerX < 9) {
            engine.leaveTile();
            playerX++;
            engine.updateGame();
            System.out.println("You have moved down.");
            engine.action = "You have moved down.";
        }
        else {
            System.out.println("You can't move there!");
            engine.action = "You can't move there!";
        }
    }


    /**
     * This method moves the player right.
     *
     * When called checks if the player is moving inside of the map otherwise warns player they can't move there.
     *
     * If the move is valid, removed the player from the previous tile move the player position then update the game with new positioning.
     *
     * After the player has moved, prints to console a message and updates the action variable.
     */
        public void moveRight() {
        if (playerY < 9) {
            engine.leaveTile();
            playerY++;
            engine.updateGame();
            System.out.println("You have moved right.");
            engine.action = "You have moved right.";
        }
        else {
            System.out.println("You can't move there!");
            engine.action = "You can't move there!";
        }
    }


    /**
     * This method moves the player left.
     *
     * When called checks if the player is moving inside of the map otherwise warns player they can't move there.
     *
     * If the move is valid, removed the player from the previous tile move the player position then update the game with new positioning.
     *
     * After the player has moved, prints to console a message and updates the action variable.
     */
    public void moveLeft() {
        if (playerY > 0) {
            engine.leaveTile();
            playerY--;
            engine.updateGame();
            System.out.println("You have moved left.");
            engine.action = "You have moved left.";
        }
        else {
            System.out.println("You can't move there!");
            engine.action = "You can't move there!";
        }
    }

    /**
     * Returns results to a string for system.out game visualisation.
     *
     * @return
     */
    public String toString(){
    String result = "";
    for (Tile[] row : board) {
            for (Tile t : row) {
                result +=t + " | ";
            }
            result += "\n";
        }
        return result;
}

}


