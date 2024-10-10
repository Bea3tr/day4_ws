package fc;

import java.io.*;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cookie {

    public BufferedReader open(File cookieFile) throws FileNotFoundException {
        Reader read = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(read);
        return br;
    }

    public void close(BufferedReader br) throws IOException{
        br.close();
    }

    public String getCookie(BufferedReader br) throws FileNotFoundException, IOException {

        List<String> cookies = new LinkedList<String>();

        String cookie = "";
        while(cookie != null) {
            cookie = br.readLine();
            if(cookie == null)
                break;
            cookies.add(cookie);
        }

        Random rand = new SecureRandom();
        int r = rand.nextInt(cookies.size());
        String randCookie = cookies.get(r);
        return randCookie;
    }
}
