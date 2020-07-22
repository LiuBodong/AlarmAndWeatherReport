package sdu.edu;

import sdu.edu.util.PlayVoiceUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        WeatherForecast weatherForecast = new WeatherForecast("101120512");
        Weather weather = weatherForecast.getCurrentWeatherReport();
        StringBuilder sb = new StringBuilder(weather.toString());
        sb.append(",");
        List<String> types = Arrays.asList("3", "9", "14", "16");
        Map<String, Indices> idxs = weatherForecast.getCurrentIndices();
        for (String type : types) {
            sb.append(idxs.get(type).toString());
        }
        Speech speech = new Speech();
        String filePath = speech.speech(sb.toString(), null);
        if (filePath != null) {
            PlayVoiceUtil.play(filePath);
        }
    }
}
