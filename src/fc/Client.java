package fc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    // args - locahost:12345
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        String[] inputs = args[0].split(":");
        String host = inputs[0];
        int port = Integer.parseInt(inputs[1]);

        // Create client socket
        System.out.println("Connecting to server...");
        Socket sock = new Socket(host, port);
        System.out.println("Connected");

        // Get output stream
        OutputStream os = sock.getOutputStream();
        Writer write = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(write);

        // Get input stream
        InputStream is = sock.getInputStream();
        Reader read = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(read);

        Console cons = System.console();
        String msg = cons.readLine("> ");

        bw.write(msg);
        bw.newLine();
        bw.flush();
        os.flush();

        String fromServer = br.readLine();
        System.out.printf(">>>Cookie requested: %s\n", fromServer);

        os.close();
        is.close();
        sock.close();
    }
}
