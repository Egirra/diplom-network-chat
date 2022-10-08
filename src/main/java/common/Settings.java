package common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings {
    private static Date dateNow = new Date();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final String PATH = "src/main/resources/settings.json";
    private static File log = new File(PATH);

    public static void log(String logPath, String userName, String msg) {
        String line = dateFormat.format(dateNow) + " [" + userName + "] : " + msg + "\n";
        try (FileWriter writer = new FileWriter(logPath, true)) {
            writer.write(line);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getResource(String key) {
        try (FileReader reader = new FileReader(PATH)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get(key);
        } catch (IOException | ParseException ex) {
            return "";
        }
    }
}