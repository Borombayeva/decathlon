import javax.xml.bind.JAXBContext;
import java.io.File;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {

        URL csvUrl = DecathlonCalculator.class.getClassLoader().getResource("csv/results.csv");
        URL xmlUrl = DecathlonCalculator.class.getClassLoader().getResource("xml/result.xml");

        DecathlonCalculator decathlonCalculator = new DecathlonCalculator(csvUrl);
        AthletesXmlDTO marshaller = decathlonCalculator.count();

        JAXBContext jaxbContext = JAXBContext.newInstance(AthletesXmlDTO.class);
        javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(marshaller, new File(xmlUrl.toURI()));
        jaxbMarshaller.marshal(marshaller, System.out);
    }
}
