package CO2017.exercise3.masf1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GuessGameServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java GuessGameServer <port> <maximum target> <time limit> ");
            System.exit(-1);
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        int port = Integer.valueOf(args[0]);
        System.out.printf("Starting GuessGame server (%s, %s) on port %s%n", args[1], TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(args[2])) , args[0]);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                //Pass the connection to a new handler and start it
                Socket client = server.accept();
                Thread h = new Thread(new GuessGameServerHandler(Integer.valueOf(args[1]), Long.valueOf(args[2]), client));
                executor.execute(h);
                // Thread h = new Thread(new GuessGameServerHandler(Integer.valueOf(args[1]), Long.valueOf(args[2]), client));
                // h.start();

            }
        }
    }
}
