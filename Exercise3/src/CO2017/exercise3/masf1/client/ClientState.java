package CO2017.exercise3.masf1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;

/**
 * Class representing the state of the client, and for handling local user interaction.
 */
public class ClientState implements Runnable {

    /**
     * volatile as it will be changed by different threads
     */
    volatile boolean _finished;

    /**
     * System.in as the buffered reader.
     */
    static BufferedReader _tty = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Output stream connected to the server.
     */
    private PrintWriter o;

    /**
     * Last input by the user.
     */
    private String lastInput;

    public ClientState(PrintWriter o) {
        _finished = false;
        this.o = o;
    }

    /**
     * Utility method that provides an interruptable console readLine.
     *
     * @param reader  - the input stream to read a line from
     * @param timeout - length of time to wait before throwing an exception
     * @return the String entered on the input stream
     * @throws TimeoutException - if no input has occured within the timeout
     * @throws IOException      - if the reader stream has unexpectedly closed
     */

    public String readLineTimeOut(BufferedReader reader, long timeout) throws TimeoutException, IOException {

        long currentTime = System.currentTimeMillis();

        while (!reader.ready()) {

            // check the guess hasnt taken longer than the timeout
            if (System.currentTimeMillis() - currentTime >= timeout)
                throw new TimeoutException("Took too long!!!!!!!!!");

            // delay between each guess
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return reader.readLine();
    }

    /**
     * Used to print a message for the local user.
     *
     * @param end - true if the game is over (and therefore no need to prompt for another guess); this will only happen once per game.
     * @param msg - is the message to be displayed to the user (NOT terminated by a newline, so this method needs to add one).
     */
    void userPrint(Boolean end, String msg) {

        if (!end) {
            System.out.print("Enter guess: ");
            return;
        }

        System.out.println();
        System.out.println(msg);

    }

    /**
     * Accessor method to return the most recent user input.
     *
     * @return the most recent user input; return null if the user has not yet made any input.
     */
    String getLastInput() {
        return this.lastInput;
    }

    /**
     * @return if the game is over or not.
     */
    boolean is_finished() {
        return _finished;
    }

    @Override
    public void run() {

        while (!_finished) {
            try {
                lastInput = readLineTimeOut(_tty, 500);
                o.printf("%s%n", lastInput);
            } catch (TimeoutException e) {
                System.err.println("Took too long to enter something.");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error retrieving input");
                e.printStackTrace();
            }
        }

    }
}
