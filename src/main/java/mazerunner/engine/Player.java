package mazerunner.engine;

public class Player {

    private int stamina = 12;
    private int gold = 0;


    /**
     * When run, resets the players stats for a new game.
     */
    public void resetPlayer() {
        this.stamina = 12;
        this.gold = 0;
    }

    public int getStamina() {
        return stamina;
    }

    public int getGold() {
        return gold;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }


    /**
     *  when called, reduces stamina by 1.
     */
    public void decrementStamina() {
        stamina--;
    }


    /**
     *  when called, reduces gold by 1.
     */
    public void decrementGold() {

        gold--;
    }


    /**
     *  when called, increased gold by 1.
     */
    public void incrementGold() {
        gold++;
    }
}