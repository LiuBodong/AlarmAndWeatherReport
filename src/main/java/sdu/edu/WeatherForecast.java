package sdu.edu;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import sdu.edu.util.PropertiesUtil;

import java.util.*;

public class WeatherForecast {

    private final String locationId;
    private final String location;
    private final String apiUrl;
    private final String locationUrl;
    private final String indicesUrl;
    private final String key;
    private final String lang;
    private final String unit;
    private final OkHttpClient client;

    public WeatherForecast(String locationId) throws Exception {
        this.locationId = locationId;
        Properties properties = PropertiesUtil.load("weather");
        this.apiUrl = properties.getProperty("url");
        this.locationUrl = properties.getProperty("location_url");
        this.indicesUrl = properties.getProperty("indices_url");
        this.key = properties.getProperty("key");
        this.lang = properties.getProperty("lang");
        this.unit = properties.getProperty("unit");
        this.client = new OkHttpClient();
        this.location = getLocationById(locationId);
    }

    /**
     * 新建Get请求
     *
     * @param url 请求Url
     * @return Get Request
     */
    private Request newGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    /**
     * 将Response解析为Json
     *
     * @param response Response
     * @return JSONObject
     */
    private JSONObject jsonResponse(Response response) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (response.code() == 200) {
                String responseString = Objects.requireNonNull(response.body()).string();
                jsonObject = new JSONObject(responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 根据LocationId获取地区名称
     *
     * @param locationId 地区ID
     * @return 地区名称
     */
    private String getLocationById(String locationId) {
        String res = "";
        String url = locationUrl + "?" +
                "key=" + key + // key
                "&" +
                "location=" + locationId + // locationID
                "&" +
                "range=cn" + // 搜索地区
                "&" +
                "mode=exact"; // 精确搜索
        try (Response response = client.newCall(newGetRequest(url)).execute()) {
            JSONObject jsonObject = jsonResponse(response);
            if (jsonObject.has("location")) {
                JSONArray locationJson = jsonObject.getJSONArray("location");
                if (locationJson.length() > 0) {
                    JSONObject location = locationJson.getJSONObject(0);
                    // 省
                    String adm1 = location.getString("adm1").trim();
                    // 市
                    String adm2 = location.getString("adm2").trim();
                    // 区县
                    String name = location.getString("name").trim();
                    StringBuilder sb = new StringBuilder(adm1);
                    //如果省和市是一样的，说明是直辖市，只取其中一个
                    if (adm1.equals(adm2)) {
                        sb.append("市");
                    } else {
                        sb.append("省");
                        sb.append(adm2).append("市");
                    }
                    sb.append(name).append("区县");
                    res = sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取当前天气情况
     *
     * @return Weather
     */
    public Weather getCurrentWeatherReport() {
        String url = apiUrl + "?" +
                "location=" + locationId +
                "&" +
                "key=" + key +
                "&" +
                "lang=" + lang +
                "&" +
                "unit=" + unit;

        Weather weather = null;
        try (Response response = client.newCall(newGetRequest(url)).execute()) {
            JSONObject jsonObject = jsonResponse(response);
            if (jsonObject.has("code") && jsonObject.getString("code").equals("200")) {
                JSONObject now = jsonObject.getJSONObject("now");
                int temp = Integer.parseInt(now.getString("temp"));
                String text = now.getString("text");
                String windDir = now.getString("windDir");
                int windScale = Integer.parseInt(now.getString("windScale"));
                int humidity = Integer.parseInt(now.getString("humidity"));
                weather = new Weather(locationId, location, temp, text, windDir, windScale, humidity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * 获取当前全部生活指数
     *
     * @return
     */
    public Map<String, Indices> getCurrentIndices() {
        Map<String, Indices> indices = new HashMap<>(40);
        String url = indicesUrl + "?" +
                "key=" + key +
                "&" +
                "location=" + locationId +
                "&" +
                "type=0";
        try (Response response = client.newCall(newGetRequest(url)).execute()) {
            JSONObject jsonObject = jsonResponse(response);
            if (jsonObject.has("code") && jsonObject.getString("code").equals("200")) {
                JSONArray daily = jsonObject.getJSONArray("daily");
                for (int i = 0; i < daily.length(); i++) {
                    JSONObject index = daily.getJSONObject(i);
                    String type = index.getString("type");
                    String name = index.getString("name");
                    String level = index.getString("level");
                    String category = index.getString("category");
                    String text = index.getString("text");
                    Indices idx = new Indices(Integer.parseInt(type), name, Integer.parseInt(level), category, text);
                    indices.put(type, idx);
                    indices.put(name, idx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indices;
    }

    //TODO 其他方式

}
