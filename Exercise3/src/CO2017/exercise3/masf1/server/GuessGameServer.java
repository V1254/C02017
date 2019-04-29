package CO2017.exercise3.masf1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class to control server side of the Guessing Game system.
 */
public class GuessGameServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java GuessGameServer <port> <maximum target> <time limit> ");
            System.exit(-1);
        }

        int port = Integer.valueOf(args[0]);
        ServerSocket server = new ServerSocket(port);
        long timeLimitMillis = TimeUnit.SECONDS.toMillis(Long.valueOf(args[2]));
        System.out.printf("Starting GuessGame server (%s, %s) on port %s%n", args[1], timeLimitMillis, args[0]);

        // manage threads
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        while (true) {
            //Pass the connection to a new handler and start it
            Socket client = server.accept();
            GuessGameServerHandler handler = new GuessGameServerHandler(Integer.valueOf(args[1]), timeLimitMillis, client);
            executor.execute(handler);
        }
    }
}
