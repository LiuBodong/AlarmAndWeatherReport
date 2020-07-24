package sdu.edu.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PlayVoiceUtil {

    public static void play(String playerExecPath, String fileName) {
        List<String> command = Arrays.asList(playerExecPath, fileName);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        // 重定向错误输出
        processBuilder.redirectErrorStream(true);
        try {
            Process p = processBuilder.start();
            // 获取子进程输入流
            InputStream inputStream = p.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待执行完成
            int exitValue = p.waitFor();
            if (exitValue == 0) {
                System.out.println("执行成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
        } finally {
            // 删除语音文件
            if (Paths.get(fileName).toFile().delete()) {
                System.out.println("清除语音文件成功");
            }
        }
    }

}
