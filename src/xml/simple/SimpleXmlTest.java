package xml.simple;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class SimpleXmlTest {

    public static void main(String[] args) throws IOException {
        {
            Settings settings = new Settings();

            Setting s1 = new Setting();
            s1.setName("PlayerName");
            s1.setValue("John");

            Setting s2 = new Setting();
            s2.setName("HP");
            s2.setValue("2000");

            List<Setting> list = new ArrayList<>();
            list.add(s1);
            list.add(s2);
            settings.setSettings(list);

            try (OutputStream os = new FileOutputStream("simple_settings.xml");
                 XMLEncoder encoder = new XMLEncoder(os)) {
                encoder.writeObject(settings);
            }
        }
        {
            try (InputStream is = new FileInputStream("simple_settings.xml");
                 XMLDecoder decoder = new XMLDecoder(is)) {
                Settings settings = (Settings) decoder.readObject();
                for (Setting s : settings.getSettings()) {
                    System.out.println(s.getName() + " = " + s.getValue());
                }
            }
        }
    }
}
