package sdu.edu;

import sdu.edu.util.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.*;
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

    private static final String playerCmd = properties.getProperty("player_exec");
    private static final String per = properties.getProperty("per");

    private static Speech speech;

    static {
        try {
            speech = new Speech();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String settingFile = properties.getProperty("setting_file");
    private static final Tuple2<List<ScheduleUtil.NoDisturbTime>, Map<Integer, String>> information = ScheduleUtil.getDailyInformation(settingFile);

    public static void main(String[] args) throws Exception {
        System.out.println(nextScheduleInterval() / 1000 / 60 / 60);
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.schedule();
//
//        String now = nowTimeGreeting();
//        WeatherForecast weatherForecast = new WeatherForecast("101120512");
//        Weather weather = weatherForecast.getCurrentWeatherReport();
//        play(weather.toString());
//        List<String> types = Arrays.asList("穿衣指数", "紫外线指数", "化妆指数", "晾晒指数", "防晒指数");
//        Map<String, Indices> idxs = weatherForecast.getCurrentIndices();
//        for (String type : types) {
//            play(idxs.get(type).toString());
//        }

    }

    private static long nextScheduleInterval() {
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault());
        int day = now.getDayOfMonth();
        int hour = now.getHour() + 1;
        for (ScheduleUtil.NoDisturbTime noDisturbTime : information.f1()) {
            int start = noDisturbTime.getStartHour();
            int end = noDisturbTime.getEndHour();
            if (start > end) {
                end += 24;
                day += 1;
            }
            if (hour < end && hour > start) {
                hour = end % 24;
            }
        }
        ZonedDateTime next = ZonedDateTime.of(now.getYear(), now.getMonthValue(),
                day, hour, 0, 0, 0,
                ZoneId.systemDefault());
        return next.toInstant().toEpochMilli() - now.toInstant().toEpochMilli();
    }

    private static String nowTimeGreeting() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        int year = zonedDateTime.getYear();
        int month = zonedDateTime.getMonthValue();
        int day = zonedDateTime.getDayOfMonth();
        int hour = zonedDateTime.getHour();
        return String.format("现在是北京时间,%s年%s月%s日%s点整,%s", int2Zh(year),
                int2Zh(month), int2Zh(day), int2Zh(hour), information.f2().getOrDefault(hour, ""));
    }

    private static String int2Zh(int n) {
        String[] nums = new String[]{
                "零", "一", "二", "三", "四", "五", "六", "七", "八", "九",
                "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七",
                "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四"
        };
        if (n <= 24) {
            return nums[n];
        }
        StringBuilder str = new StringBuilder();
        while (n > 0) {
            str.append(nums[n % 10]);
            n = n / 10;
        }
        return str.reverse().toString();
    }

    private static void play(String text) {
        String filePath = speech.speech(text, per);
        if (filePath != null) {
            PlayVoiceUtil.play(playerCmd, filePath);
        }
    }
}
