import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DecathlonCalculator {

    private final URL csvUrl;

    public DecathlonCalculator(URL csvUrl) {
        this.csvUrl = csvUrl;
    }

    public AthletesXmlDTO count() throws Exception {

        List<String> lines = Files.lines(Paths.get(csvUrl.toURI())).filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

        List<AthleteXmlDTO> athletes = new ArrayList<>();

        for (String s : lines) {

            AthleteXmlDTO athleteXmlDTO = new AthleteXmlDTO();
            List<Result> results = new ArrayList<>();

            String[] split = s.split(";");

            for (int i = 1; i < split.length; i++) {
                String val = split[i];

                ScoreMap scoreMap = ScoreMap.valueFrom(i);
                Double dblVal = parse(val, scoreMap.unit);

                Result result = new Result();
                result.setEventName(scoreMap.event);
                result.setPoints(calculateOneEvent(dblVal, scoreMap));
                result.setValue(dblVal);
                results.add(result);
            }

            athleteXmlDTO.setName(split[0]);
            athleteXmlDTO.setResultList(results);
            athleteXmlDTO.setTotalScore(calculateTotalScore(athleteXmlDTO.getResultList()));
            athletes.add(athleteXmlDTO);

        }

        List<AthleteXmlDTO> sorted = athletes.stream().sorted(Comparator.comparing(AthleteXmlDTO::getTotalScore).reversed()).collect(Collectors.toList());
        setPlaces(athletes);

        AthletesXmlDTO athletesXmlDTO = new AthletesXmlDTO();
        athletesXmlDTO.setAthleteList(sorted);

        return athletesXmlDTO;
    }

    private static Double parse(String val, String unit) {
        Double secOrMeterValue = null;
        if (unit.equals("m:s")) {
            String[] split = val.split("\\.");
            if (split.length > 0) {
                int minutes = Integer.parseInt(split[0]);
                double seconds = Double.parseDouble(split[1] + "." + split[2]);
                secOrMeterValue = minutes * 60 + seconds;
            }
        } else
            secOrMeterValue = Double.parseDouble(val);
        return secOrMeterValue;
    }


    private static double calculateOneEvent(Double value, ScoreMap map) {
        if (map.needConvertToCm) {
            value = value * 100;
        }
        return Math.floor(Math.pow(Math.abs(map.b - value), map.c) * map.a);
    }

    private double calculateTotalScore(List<Result> results) {
        double total = 0.0;
        for (Result result : results) {
            total = total + result.getPoints();
        }
        return total;
    }

    private void setPlaces(List<AthleteXmlDTO> athletes) {
        for (AthleteXmlDTO athlete : athletes) {
            athlete.setPlace(countPlace(athlete.getTotalScore(), athletes.stream().map(AthleteXmlDTO::getTotalScore).collect(Collectors.toList())));
        }
    }

    private String countPlace(Double totalScore, List<Double> totalScores) {
        List<Double> collect = totalScores.stream().filter(k -> k.equals(totalScore)).collect(Collectors.toList());
        int place = totalScores.indexOf(totalScore) + 1;
        String placeStr = Integer.toString(place);
        if (collect.size() > 1) {
            for (int i = place + 1; i <= collect.size(); i++) {
                placeStr = placeStr + "-" + i;
            }
        }
        return placeStr;
    }


}
