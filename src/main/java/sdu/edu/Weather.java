package sdu.edu;

import static sdu.edu.util.Int2Zh.int2Zh;

public class Weather {

    private String locationId;
    private String location;
    private int temp;
    private String text;
    private String windDir;
    private int windScale;
    private int humidity;

    public Weather() {

    }

    public Weather(String locationId, String location, int temp, String text, String windDir, int windScale, int humidity) {
        this.locationId = locationId;
        this.location = location;
        this.temp = temp;
        this.text = text;
        this.windDir = windDir;
        this.windScale = windScale;
        this.humidity = humidity;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public int getWindScale() {
        return windScale;
    }

    public void setWindScale(int windScale) {
        this.windScale = windScale;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s度,%s%s级,相对湿度百分之%s", location, text, int2Zh(temp), windDir,
                int2Zh(windScale), int2Zh(humidity));
    }
}
