package CO2017.exercise3.masf1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Simple client class for playing the number guessing game.
 */
public class GuessGameClient {

    /**
     * Main client behaviour implementation.
     *
     * @param args - array of 2 command line arguments: 1. the hostname where the server is running , 2. the port the server is listening on.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java GuessGameClient <hostname> <port>");
            System.exit(-1);
        }

        // set up server and streams
        Socket server = new Socket(args[0], Integer.parseInt(args[1]));
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream(), StandardCharsets.UTF_8));
        PrintWriter out = new PrintWriter(server.getOutputStream(), true);

        ClientState client = new ClientState(out);
        new Thread(client).start();


        // response from server.
        String[] response = in.readLine().split(":");

        // out message.
        String startMessage = String.format("New guessing game. Range is 1..%s. Time limit is %ss", response[1], inSeconds(Long.parseLong(response[2])));

        // print the first message
        client.userPrint(false, startMessage);

        // read responses from server till game is finished.
        while (!client.isFinished()) {

            // read the response
            response = in.readLine().split(":");

            String scoreState = response[0].toUpperCase();

            if (scoreState.equals("HIGH") || scoreState.equals("LOW")) {
                // create the string.
                String msg = String.format("Turn %s: %s was %s, %ss remaining",
                        response[2],
                        client.getLastInput(),
                        response[0],
                        inSeconds(Long.parseLong(response[1])));

                // print to the user
                client.userPrint(false, msg);
            } else if (scoreState.equals("ERR")) {
                String msg = String.format("ERROR: Turn %s: %ss remaining",
                        response[2],
                        inSeconds(Long.parseLong(response[1])));
                client.userPrint(false, msg);
            }

            // check if the game is won
            if (scoreState.equals("WIN")) {
                String msg = String.format("Turn %s: target was %s - %s",
                        response[1],
                        response[2],
                        response[0]);
                client.userPrint(true, msg);
                client.setFinished(true);
            }
        }
        server.close();
    }

    private static double inSeconds(long t) {
        return TimeUnit.MILLISECONDS.toSeconds(t);
    }
}
