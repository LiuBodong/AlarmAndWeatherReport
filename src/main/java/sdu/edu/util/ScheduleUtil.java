package sdu.edu.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ScheduleUtil {

    public static Tuple2<List<NoDisturbTime>, Map<Integer, String>> getDailyInformation(String settingFile) {
        List<NoDisturbTime> list = new ArrayList<>();
        Map<Integer, String> greetings = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ScheduleUtil.class.getClassLoader().getResourceAsStream(settingFile))));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString()).getJSONObject("daily");
            JSONArray ndJsonArray = jsonObject.getJSONArray("no_disturb_time");
            for (int i = 0; i < ndJsonArray.length(); i++) {
                JSONObject object = ndJsonArray.getJSONObject(i);
                int start = object.getInt("start");
                int end = object.getInt("end");
                list.add(NoDisturbTime.of(start, end));
            }
            JSONArray greetingsJsonArray = jsonObject.getJSONArray("greetings");
            for (int i = 0; i < greetingsJsonArray.length(); i++) {
                JSONObject object = greetingsJsonArray.getJSONObject(i);
                int hour = object.getInt("hour");
                String text = object.getString("text");
                greetings.put(hour, text);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tuple2.of(list, greetings);
    }

    public static class NoDisturbTime {
        private int startHour;
        private int endHour;

        public static NoDisturbTime of(int startHour, int endHour) {
            return new NoDisturbTime(startHour, endHour);
        }

        public NoDisturbTime(int startHour, int endHour) {
            this.startHour = startHour;
            this.endHour = endHour;
        }

        public int getStartHour() {
            return startHour;
        }

        public void setStartHour(int startHour) {
            this.startHour = startHour;
        }

        public int getEndHour() {
            return endHour;
        }

        public void setEndHour(int endHour) {
            this.endHour = endHour;
        }

        @Override
        public String toString() {
            return "NoDisturbTime {" +
                    "startHour=" + startHour +
                    ", endHour=" + endHour +
                    '}';
        }
    }

}
