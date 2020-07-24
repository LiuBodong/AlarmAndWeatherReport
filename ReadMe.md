# 自动报时与天气预报

这是一个使用JAVA语言开发的自动报时与天气预报系统。

使用：
>   
> **安装jdk 1.8+，Maven**  
> 在项目目录下执行
> ```shell script
> mvn clean package
> java -jar target/AlarmAndWeatherReport-1.0-SNAPSHOT.jar
> ```

项目依赖：
>  * OkHttp3
>  * org.json  
>  * JAVA 1.8+
>  * Mplayer播放器（可用其他无界面播放器代替，如VLC，需要在app.properties中配置）
  
主要功能有：  
>  * 自动报时，以小时为单位
>  * 自动报时时播报当前天气和生活指数
>  * 自动报时时播放问候语（可自定义）
>  * 勿扰模式，可自定义时间段

配置文件说明：
* app.properties  
```properties
# 播放器可执行文件，默认为Linux下的Mplayer
player_exec=/usr/bin/mplayer
#语调 男声or女声(1-6)
per=1
# 配置文件路径，文件中定义了勿扰时间等选项
setting_file=settings.json
```  
* settings.json
```json
{
  "daily": {
    "no_disturb_time": [
      {
        "start": 20,
        "end": 8
      },
      {
        "start": 12,
        "end": 15
      }
    ],
    "greetings": [
      {
        "hour": 8,
        "text": "早上好"
      }
    ]
  }
}
```
> * daily为每日播放的选项
>> * no_disturb_time 勿扰时间，以start（开始时间）和end（结束时间）组成
>> 在start和end只间的时间不会播报  
>> * greetings 问候语，以hour（时间点）和text（问候语组成），将会在hour时间点播放text的语音内容      

* speech.properties
```properties
# API URL，采用了韩小韩API接口站的文字转语音功能 https://api.vvhan.com/
# 因为穷买不起百度/腾讯/讯飞的接口，QAQ
api_url=https://api.vvhan.com/api/song
# 临时语音文件夹存放目录，默认在当前目录的voices文件夹下
voice_dir=voices
```  
* weather.properties
```properties
# 具体请看 https://dev.heweather.com/docs/start/
# 非常不错的一个天气信息获取API，免费的就够用了，推荐一下
# 天气预报API地址
url=https://devapi.heweather.net/v7/weather/now
# 地区查询API地址
location_url=https://geoapi.heweather.net/v2/city/lookup
# 生活指数API地址
indices_url=https://devapi.heweather.net/v7/indices/1d
# API KEY 请务必自行申请APP KEY然后填写到下面
key=aff0d0ec0ee4457ca4ba704e060a1e58
# 语言
lang=cn
# 单位
unit=m
```
