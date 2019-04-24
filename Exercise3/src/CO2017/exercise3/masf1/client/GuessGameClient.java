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

       try(Socket server = new Socket(args[0],Integer.valueOf(args[1]))){

           // TODO: delete
           System.out.println("Connected to " + server.getInetAddress());

           // I/O
           BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream(), StandardCharsets.UTF_8));
           PrintWriter out = new PrintWriter(server.getOutputStream(),true);

          // new thread for client inputs
           ClientState client = new ClientState(out);
           new Thread(client).start();

           String[] response = in.readLine().split(":");
           client.userPrint(false,String.format("New guessing game. Range is 1..%s. Time limit is %ss",response[1],inSeconds(Long.valueOf(response[2]))));


           // TODO: add loop to read server response.
       }
    }

    private static double inSeconds(long t){
        return TimeUnit.MILLISECONDS.toSeconds(t);
    }
}
