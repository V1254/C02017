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

        String[] serverResponse = in.readLine().split(":");

        // out message.
        String initialMessage = String.format("New guessing game. Range is 1..%s. Time limit is %ss", serverResponse[1], inSeconds(Long.valueOf(serverResponse[2])));

        // print the first message
        client.userPrint(false, initialMessage);

        // read responses from server till game is finished.
        while (!client.isFinished()) {

            // read the response
            serverResponse = in.readLine().split(":");

            String scoreState = serverResponse[0].toUpperCase();
            String outMsg;

            // change outMsg based on scoreState from server.
            if (scoreState.equals("WIN")) {
                outMsg = String.format("Turn %s: target was %s - %s", serverResponse[1], serverResponse[2], serverResponse[0]);
                client.userPrint(true, outMsg);
                client.setFinished(true);
            } else if (scoreState.equals("HIGH") || scoreState.equals("LOW")) {
                // create the string.
                outMsg = String.format("Turn %s: %s was %s, %ss remaining", serverResponse[2], client.getLastInput(), serverResponse[0], inSeconds(Long.valueOf(serverResponse[1])));
                client.userPrint(false, outMsg);
            } else {
                outMsg = String.format("ERROR: Turn %s: %ss remaining", serverResponse[2], inSeconds(Long.valueOf(serverResponse[1])));
                client.userPrint(false, outMsg);
            }
        }
        server.close();
    }

    private static double inSeconds(long t) {
        return TimeUnit.MILLISECONDS.toSeconds(t);
    }
}
