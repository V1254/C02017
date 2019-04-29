package CO2017.exercise3.masf1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Class to handle server side of a single instance of the guessing game in its own thread.
 */
public class GuessGameServerHandler implements Runnable {

    /**
     * An instance of GameState to record the state of the game.
     */
    private GameState game;

    /**
     * The unique single character identifier of this game.
     */
    private char id;

    /**
     * The maximum value for the game. and the time limit (phase 3).
     */
    private int mv;

    /**
     * The time limit for the game.
     */
    private long timeLimit;

    /**
     * The socket that this client is connected to
     */
    private Socket client;

    /**
     * The input and output streams.
     */
    private PrintWriter out;
    private BufferedReader in;

    /**
     * integer responding to the next uppercase letter.
     */
    static int nextID = 65;

    /**
     * Create a new Handler to play one instance of the guessing game.
     *
     * @param mv - maximum value for the guessing game
     * @param tl - time limit for the game in milliseconds
     * @param c1 - socket connected to the client playing the game
     */
    public GuessGameServerHandler(int mv, long tl, Socket c1) {
        this.mv = mv;
        this.timeLimit = tl; // convert to seconds for printing.
        this.game = new GameState(mv, this.timeLimit, this);
        this.client = c1;
        this.id = (char) nextID;
        setNextID(); // set the id for the next instance.
    }


    /**
     * Increments the current nextID or resets it back to 'A' if all upper case letters used.
     */
    private void setNextID() {
        if (nextID >= 90) {
            // reset the nextId;
            nextID = 65;
        } else {
            // increment otherwise.
            nextID++;
        }
    }


    /**
     * Helper function to standardise sending responses to the client via the socket output stream.
     *
     * @param msg - protocol message to be sent to a client; NOT terminated with an end of line.
     * @throws IOException - if the socket is already closed
     */
    void send(String msg) throws IOException {
        out.printf("%s%n", msg);
    }

    /**
     * Helper function to standardise printing out server-side "log" console messages.
     *
     * @param msg - message to be displayed on the server-side console.
     */
    void log(String msg) {
        System.out.println(id + " " + msg);
    }

    /**
     * Callback method to allow the GameState to shutdown the client Socket.
     *
     * @throws IOException - if the socket is already closed.
     */
    void shutdownInput() throws IOException {
        client.shutdownInput();
    }

    /**
     * Main interaction with the client.
     */
    @Override
    public void run() {
        try {
            // Initialise the streams.
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));

            // Send the initial "START" message to the client.
            send(String.format("START:%s:%s", mv, timeLimit));

            // Start the GameState thread associated with this game
            Thread t = new Thread(game);
            t.start();

            // Print out server-side messages
            log("connection : " + client.getInetAddress());
            log("start watching");
            log("target is " + game.getTarget());

            int lastGuess = 0;
            while (true) {

                // true if the last guess was an integer.
                boolean intGuess = false;
                // true if the guess is within the range of minval to mv.
                boolean validGuess = false;
                // true if timeout exception occurs.
                boolean timedOut = false;

                // attempt to fetch the guess.
                // can prolly do this in another method to clean up this cancer long method.
                try {
                    lastGuess = Integer.valueOf(readLineTimeout(in, 1000));
                    if (lastGuess >= GameState.MINVAL && lastGuess <= mv) {
                        validGuess = true;
                    }
                    intGuess = true;
                } catch (NumberFormatException e) {
                    intGuess = false;

                } catch (TimeoutException te) {
                    timedOut = true;
                }

                if (!timedOut) {
                    if (!validGuess) {
                        send(String.format("ERR:%s:%s", game.getTimeRemaining(), game.getGuesses()));
                        // two possible reasons for not valid guess: not an int or out-of-range.
                        if (intGuess) {
                            log(String.format("%s (ERR out of range)-%ss/%s", lastGuess, game.getTimeRemainingSeconds(), game.getGuesses()));
                        } else {
                            log(String.format("%s (ERR non-integer)-%ss/%s", lastGuess, game.getTimeRemainingSeconds(), game.getGuesses()));
                        }

                    } else {
                        // submit the guess to the GameState.
                        game.guess(lastGuess);
                        if (!game.finished()) {
                            send(String.format("%s:%s:%s", game.toString(), game.getTimeRemaining(), game.getGuesses()));
                            log(String.format("%s (%s)-%ss/%s", lastGuess, game.toString(), game.getTimeRemainingSeconds(), game.getGuesses()));
                        } else {
                            // game has ended so break out of loop and log.
                            log(String.format("%s (%s)-%ss/%s", lastGuess, game.toString(), game.getTimeRemainingSeconds(), game.getGuesses()));
                            break;
                        }
                    }
                }
            }

            send(String.format("%s:%s:%s", game.toString(), game.getGuesses(), game.getTarget()));
            if (game.toString().equalsIgnoreCase("LOSE"))
                log(String.format("- (%s)--%ss/%s", game.toString(), game.getTimeRemainingSeconds(), game.getGuesses()));

            // game is over.
            log("Game over");


        } catch (IOException e) {
            log("I/O error occurred " + e.getMessage());
        }
    }

    /**
     * checks the status of the underlying stream.
     *
     * @param reader
     * @param timeout
     * @return
     * @throws TimeoutException
     * @throws IOException
     */
    private static String readLineTimeout(BufferedReader reader, long timeout)
            throws TimeoutException, IOException {
        long startTime = System.currentTimeMillis();

        while (!reader.ready()) {
            if (System.currentTimeMillis() - startTime >= timeout)
                throw new TimeoutException();

            //Delay between polling buffer
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }

        // won't block since reader is ready.
        return reader.readLine();
    }

}