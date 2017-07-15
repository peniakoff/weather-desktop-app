package pl.tomaszmiller.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Peniakoff on 15.07.2017.
 */
public class Utils {

    public static String connectAndResponse(String url) {
        StringBuffer stringBuffer = new StringBuffer(); //thread's safe!
        try {
            URLConnection connection = new URL(url).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
