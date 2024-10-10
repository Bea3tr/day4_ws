package fc;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws IOException {
        
        // Set default port
        int port = 3000;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);

        // Create a server
        ServerSocket server = new ServerSocket(port);

        while(true) {

            System.out.printf("Waiting for connection on port %d\n", port);
            Socket sock = server.accept();
            System.out.println("Connection established");

            // Get input stream
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Get output stream
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // Read cookie file & write into a list 
            File cookieFile = new File(args[1]);
            Cookie cookie = new Cookie();

            BufferedReader openCookie = cookie.open(cookieFile);
            System.out.printf("Cookie file: %s opened\n", cookieFile);

            String randCookie = cookie.getCookie(openCookie);
            System.out.printf("Random cookie: %s\n", randCookie);

            cookie.close(openCookie);
            System.out.printf("Cookie file: %s closed\n", cookieFile);

            String fromClient = br.readLine();
            if(fromClient.equals("get-cookie")) {
                bw.write("cookie-text " + randCookie);
                bw.newLine();
                bw.flush();
                os.flush();
            }
            if(fromClient.equals("close")) { 
                sock.close();
                System.out.println("Closing connection");
                System.exit(0);
            }
            os.close();
            is.close();
            sock.close();
        }
    }
}
