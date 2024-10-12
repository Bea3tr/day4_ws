package fc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.*;

public class CookieClientHandler implements Runnable {

    private final Socket sock;
    private final File cookieFile;

    public CookieClientHandler(Socket s, File cf) {
        sock = s;
        cookieFile = cf;
    };

    @Override
    public void run() {

        try {
            // Get input stream
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Get output stream
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            // // Read cookie file & write into a list 
            // File cookieFile = new File(args[1]);
            Cookie cookie = new Cookie();

            BufferedReader openCookie = cookie.open(cookieFile);
            System.out.printf("Cookie file: %s opened\n", cookieFile);

            String randCookie = cookie.getCookie(openCookie);
            // System.out.printf("Random cookie: %s\n", randCookie);

            cookie.close(openCookie);
            // System.out.printf("Cookie file: %s closed\n", cookieFile);

            String fromClient = br.readLine();
            if(fromClient.equals("get-cookie")) {
                bw.write("cookie-text " + randCookie);
                bw.newLine();
                // Flush output in reverse
                bw.flush();
                writer.flush();
                os.flush();
            }
            if(fromClient.equals("close")) { 
                sock.close();
                System.out.println("Closing connection");
                System.exit(0);
            }
            bw.close();
            writer.close();
            os.close();
            is.close();
            sock.close();
        } catch (IOException ex) {
            System.err.println("File cannot be read");
        }
    }
}
