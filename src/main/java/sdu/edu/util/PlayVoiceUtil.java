package sdu.edu.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PlayVoiceUtil {

    public static void play(String fileName) {
        String playerCmd = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
        List<String> command = Arrays.asList(playerCmd, fileName);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process p = processBuilder.start();
            InputStream inputStream = p.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitValue = p.waitFor();
            if (exitValue == 0) {
                System.out.println("执行成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
        } finally {
            if (Paths.get(fileName).toFile().delete()) {
                System.out.println("清除语音文件成功");
            }
        }
    }

}
