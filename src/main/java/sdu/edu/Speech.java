package sdu.edu;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import sdu.edu.util.PropertiesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

/*
 *请求API，获取语音
 */
public class Speech {

    private final String apiUrl;
    private final String voiceDir;
    private final OkHttpClient client;

    public Speech() throws Exception {
        Properties properties = PropertiesUtil.load("speech");
        this.voiceDir = properties.getProperty("voice_dir");
        this.apiUrl = properties.getProperty("api_url");
        this.client = new OkHttpClient();
    }

    public String speech(String text, String per) {
        if (per == null) {
            per = "6"; // 默认语调
        }
        if (text.getBytes().length >= 1024) {
            throw new IllegalArgumentException("Text is too large!");
        }
        text = text.trim().replaceAll(" ", "");
        String res = null;
        // 发送GET请求
        String url = apiUrl + "?txt=" + text + "&per=" + per;
        System.out.println(url);
        Request request = new Request.Builder().get().url(url)
                .addHeader("Connection", "keep-alive")
                .build();
        JSONObject result = null;
        byte[] data = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                ResponseBody body = response.body();
                assert body != null;
                // 如果类型是application，说明出错
                if (Objects.requireNonNull(body.contentType()).type().equals("application")) {
                    result = new JSONObject(body.string());
                } else if (Objects.requireNonNull(body.contentType()).type().equals("audio")) {
                    // 解析成功，获取数据
                    data = body.bytes();
                }
            } else {
                System.out.println("请求失败：" + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null && data != null) {
            // 给文件一个随机名
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();
            try {
                File dir = new File(voiceDir);
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                        System.out.println("创建文件夹:" + voiceDir);
                    }
                } else {
                    if (dir.isFile()) {
                        throw new IllegalArgumentException(voiceDir + " 已经以文件形式存在，请删除~");
                    }
                }
                // 写入数据
                File destFile = Paths.get(voiceDir, fileName).toFile();
                FileOutputStream outputStream = new FileOutputStream(destFile);
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
                res = destFile.getPath();
            } catch (Exception e) {
                // 出错则删除文件
                e.printStackTrace();
                File file = Paths.get(voiceDir, fileName).toFile();
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("获取语音失败，清除无用语音文件");
                    }
                }
            }
        } else {
            // 获取出错则打印出错结果
            if (result != null) {
                System.out.println(result.toString(2));
            }
        }
        return res;
    }

}
