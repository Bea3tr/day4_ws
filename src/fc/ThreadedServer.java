package fc;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ThreadedServer {

    public static void main(String[] args) throws IOException {
        
        // Create a threadpool
        ExecutorService thrPool = Executors.newFixedThreadPool(3);
        
        // Set default port
        int port = 3000;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create a server
        ServerSocket server = new ServerSocket(port);
        File cookieFile = new File(args[1]);

        while(true) {

            System.out.printf("Waiting for connection on port %d\n", port);
            Socket sock = server.accept();
            System.out.println("Connection established");

            CookieClientHandler handler = new CookieClientHandler(sock, cookieFile);

            // Pass the work to the worker
            thrPool.submit(handler);
        }
    }
}
