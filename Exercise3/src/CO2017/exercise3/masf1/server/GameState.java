package CO2017.exercise3.masf1.server;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Class to represent the state of an instance of the guessing game on the server side.
 */
public class GameState implements Runnable {

    /**
     * class constant: minimum allowed guess (set to 1)
     */
    static int MINVAL = 1;

    /**
     * Used to generate a new random target number in the range for each new game.
     */
    static Random RANDGEN = new Random();

    /**
     * the maximum value to be used in the quessing game.
     */
    int mv;

    /**
     * The target for this game; which will be a random number between MINVAL and mv.
     */
    int target;

    /**
     * The number of guesses made so far.
     */
    int guesses;

    /**
     * Whether the game is over.
     */
    private boolean finished;

    /**
     * The current "state" of the game based on the most recent guess and the time remaining.
     */
    private String gameState;

    /**
     * The time limit (in milliseconds) for the game.
     */
    private long tl;

    /**
     * The game the time will end after starting in the run method.
     */
    private long endingTime;

    /**
     * the handler that is playing this instance of the game.
     */
    private GuessGameServerHandler ggsh;

    /**
     * Will setup an instance of the game.
     *
     * @param mv   - the maximum value to be used in the quessing game; guesses will be in the range MINVAL..mv
     * @param tl   - the time limit (in milliseconds) for the game.
     * @param ggsh - the handler that is playing this instance of the game.
     */
    public GameState(int mv, long tl, GuessGameServerHandler ggsh) {
        this.mv = mv;
        this.tl = tl;
        this.ggsh = ggsh;
        this.guesses = 0;
        this.target = RANDGEN.nextInt(mv) + 1;
    }

    /**
     * Get the target value for this game.
     *
     * @return the target number for this instance of the game
     */
    public int getTarget() {
        return this.target;
    }

    /**
     * Get the number of guesses made so far.
     *
     * @return the number of guesses made so far.
     */
    public int getGuesses() {
        return this.guesses;
    }

    /**
     * Is the game over?
     *
     * @return true if and only if the game is over (either because the player guessed correctly, or because time is up
     */
    public boolean finished() {
        return this.finished;
    }

    /**
     * How many milliseconds before the game is over.
     *
     * @return the amount of time (in milliseconds) still remaining for the player to guess the number.
     */
    public long getTimeRemaining() {
        long timeRemaining = this.endingTime - System.currentTimeMillis();
        return timeRemaining > 0 ? timeRemaining : 0;
    }

    public double getTimeRemainingSeconds(){
        return TimeUnit.MILLISECONDS.toSeconds(getTimeRemaining());
    }

    /**
     * Make a guess.
     *
     * @param guess -  a guess.
     */
    public void guess(int guess) {
        if (getTimeRemaining() <= 0) {
            this.gameState = "LOSE";
        } else if (guess == target) {
            this.gameState = "WIN";
            this.finished = true;
        } else if (guess > target) {
            this.gameState = "HIGH";
        } else {
            this.gameState = "LOW";
        }
        this.guesses++;
    }


    public String toString() {
        return this.gameState;
    }


    /**
     * Start and monitor the game.
     */
    @Override
    public void run() {
        //Initialise endingTime to be now + time limit
        this.endingTime = System.currentTimeMillis() + tl;

        while (!finished) {
            //Has the game ended?
            if (endingTime < System.currentTimeMillis()) {
                gameState = "LOSE";
                finished = true;
                try {
                    ggsh.shutdownInput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }


}
