package sdu.edu;

import sdu.edu.bean.Indices;
import sdu.edu.bean.Tuple2;
import sdu.edu.bean.Weather;
import sdu.edu.util.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception {
        schedule();
    }

    /**
     * 开始调用执行
     */
    private static void schedule() {
        // 下次执行的时间
        long nextInterval = nextScheduleInterval();
        long minutes = nextInterval / 1000 / 60;
        long hour = minutes / 60;
        minutes = minutes % 60;
        // 打印下次执行时间
        System.out.println("下次调用在：" + hour + "小时" + minutes + "分钟后～");
        service.schedule(() -> {
            try {
                run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, nextInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * 程序执行
     *
     * @throws Exception
     */
    private static void run() throws Exception {
        // 当前时间打印
        String nowGreeting = nowTimeGreeting();
        System.out.println(nowGreeting);
        play(nowGreeting);
        WeatherForecast weatherForecast = new WeatherForecast("101120512");
        Weather weather = weatherForecast.getCurrentWeatherReport();
        System.out.println(weather);
        play(weather.toString());
        List<String> types = Arrays.asList("穿衣指数", "紫外线指数", "化妆指数", "晾晒指数", "防晒指数");
        Map<String, Indices> idxs = weatherForecast.getCurrentIndices();
        for (String type : types) {
            String str = idxs.get(type).toString();
            System.out.println(str);
            play(str);
        }
        schedule();
    }

    /**
     * 获取距离下次调用的毫秒数
     *
     * @return
     */
    private static long nextScheduleInterval() {
        ZonedDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault());
        int day = now.getDayOfMonth();
        // 下一个小时
        int hour = now.getHour() + 1;
        // 处理勿扰时间
        for (ScheduleUtil.NoDisturbTime noDisturbTime : information.f1()) {
            int start = noDisturbTime.getStartHour();
            int end = noDisturbTime.getEndHour();
            // 如果开始时间大于结束时间，则说明跨天，天数+1
            if (start > end) {
                if ((hour > start && hour <= 24) || (hour >= 0 && hour < end)) {
                    day += 1;
                    hour = end;
                }
            } else if (hour < end && hour > start) {
                // 如果跨天就需要除24取余
                hour = end;
            }
        }
        ZonedDateTime next = ZonedDateTime.of(now.getYear(), now.getMonthValue(),
                day, hour, 0, 0, 0,
                ZoneId.systemDefault());
        return next.toInstant().toEpochMilli() - now.toInstant().toEpochMilli();
    }

    /**
     * 获取当前时间和问候语
     *
     * @return
     */
    private static String nowTimeGreeting() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        int year = zonedDateTime.getYear();
        int month = zonedDateTime.getMonthValue();
        int day = zonedDateTime.getDayOfMonth();
        int hour = zonedDateTime.getHour();
        return String.format("现在是北京时间,%s年%s月%s日%s点整,%s", int2Zh(year),
                int2Zh(month), int2Zh(day), int2Zh(hour), information.f2().getOrDefault(hour, ""));
    }

    /**
     * 数字转中文，
     * 大于31，例如1994则会转为一九九四
     * 小于等于31，例如22则会转为二十二
     *
     * @param n
     * @return
     */
    private static String int2Zh(int n) {
        String[] nums = new String[]{
                "零", "一", "二", "三", "四", "五", "六", "七", "八", "九",
                "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七",
                "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四",
                "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"
        };
        if (n <= 31) {
            return nums[n];
        }
        StringBuilder str = new StringBuilder();
        while (n > 0) {
            str.append(nums[n % 10]);
            n = n / 10;
        }
        return str.reverse().toString();
    }

    // 播放语音
    private static void play(String text) {
        String filePath = speech.speech(text, per);
        if (filePath != null) {
            PlayVoiceUtil.play(playerCmd, filePath);
        }
    }
}
