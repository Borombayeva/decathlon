import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class DecathlonCalculatorTest {

    @Test
    public void checkMarshaller() throws Exception {

        URL csvUrl = DecathlonCalculator.class.getClassLoader().getResource("csv/results.csv");

        DecathlonCalculator decathlonCalculator = new DecathlonCalculator(csvUrl);
        AthletesXmlDTO marshaller = decathlonCalculator.count();

        int expected = Files.lines(Paths.get(csvUrl.toURI())).filter(line -> !line.isEmpty())
                .collect(Collectors.toList()).size();
        checkSize(expected, marshaller.getAthleteList());
        checkScores(marshaller.getAthleteList());
        checkPlaces(expected, marshaller.getAthleteList());

    }

    private void checkSize(int expected, List<AthleteXmlDTO> athletes) {
        Assert.assertEquals(expected, athletes.size());
    }

    private void checkScores(List<AthleteXmlDTO> athletes) {
        for (AthleteXmlDTO athleteXmlDTO : athletes) {
            Assert.assertNotNull(athleteXmlDTO.getTotalScore());
            Assert.assertTrue(athleteXmlDTO.getTotalScore() > 0);
        }
    }

    private void checkPlaces(Integer athleteCnt, List<AthleteXmlDTO> athletes) {
        for (AthleteXmlDTO athlete : athletes) {
            Assert.assertNotNull(athlete.getPlace());
            String[] places = athlete.getPlace().split("-");
            for (String s : places) {
                int place = Integer.parseInt(s);
                Assert.assertTrue(place <= athleteCnt);
            }
        }
    }
}
