package xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.List;

public final class JaxbTest {

    public static void main(String[] args) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class);
        {
            // 1. Запись объекта в XML:
            // a) создаем объект
            Settings settings = new Settings();

            SettingType s1 = new SettingType();
            s1.setName("PlayerName");
            s1.setValue("John");

            SettingType s2 = new SettingType();
            s2.setName("HP");
            s2.setValue("2000");

            settings.getSetting().add(s1);
            settings.getSetting().add(s2);

            // b) сохраняем в XML
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(settings, new File("settings.xml"));
        }
        {
            // 2. Чтение объекта из XML:
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Source src = new StreamSource(new File("settings.xml"));
            Settings settings = unmarshaller.unmarshal(src, Settings.class).getValue();
            List<SettingType> setting = settings.getSetting();
            for (SettingType s : setting) {
                System.out.println(s.getName() + " = " + s.getValue());
            }
        }
    }
}
