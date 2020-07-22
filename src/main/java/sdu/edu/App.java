package sdu.edu;

import sdu.edu.util.PlayVoiceUtil;
import sdu.edu.util.PropertiesUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class App {

    private static Properties properties;

    static {
        try {
            properties = PropertiesUtil.load("app");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String playerCmd = properties.getProperty("player_exec");
    private static String per = properties.getProperty("per");

    private static Speech speech;

    static {
        try {
            speech = new Speech();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.schedule();

        WeatherForecast weatherForecast = new WeatherForecast("101120512");
        Weather weather = weatherForecast.getCurrentWeatherReport();
        play(weather.toString());
        List<String> types = Arrays.asList("穿衣指数", "紫外线指数", "化妆指数", "晾晒指数", "防晒指数");
        Map<String, Indices> idxs = weatherForecast.getCurrentIndices();
        for (String type : types) {
            play(idxs.get(type).toString());
        }

    }

    public static long nextScheduleInterval() {
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault());
        int day = now.getDayOfMonth();
        int hour = now.getHour() + 1;
        if (hour < 8) {
            hour = 8;
        }
        if (hour > 20) {
            hour = 8;
            day += 1;
        }
        ZonedDateTime next = ZonedDateTime.of(now.getYear(), now.getMonthValue(),
                day, hour, 0, 0, 0,
                ZoneId.systemDefault());
        return next.toInstant().toEpochMilli() - now.toInstant().toEpochMilli();
    }

    public static void play(String text) {
        String filePath = speech.speech(text, per);
        if (filePath != null) {
            PlayVoiceUtil.play(playerCmd, filePath);
        }
    }
}
