package edu.zut.weatherforcast.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {
    //https://tianqiapi.com/api?version=v1&appid=36646344&appsecret=c1lgQbP9
    //https://v0.yiketianqi.com/api?unescape=1&version=v91&appid=43656176&appsecret=I42og6Lm
    // 一天只有十次机会
    /**
     * 18625561：27XjzrB7
     * 67342285：5XgTk31r
     * 19267789：Dhu3DShY
     */
    public static final String URL_WEATHER = "https://tianqiapi.com/api?version=v1&appid=18625561&appsecret=27XjzrB7";

    public static String getWeather() {
        String result = "";
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // 连接网络
        try {
            URL urL = new URL(URL_WEATHER);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 从连接中读取数据(二进制)
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            // 二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从缓存区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return result;
    }
//    /**
//     * 对json字符串进解析
//     * @param jsonStr json字符串
//     */
//    public void jsonParse(String jsonStr) throws JSONException {
//        //根据json字符串格式生成json对象或json数组
//        JSONObject weather = new JSONObject(jsonStr);
//        //逐级获取对应的属性信息
//        String city = weather.getString("city");
//        String updateTime = weather.getString("update_time");
//        JSONObject data = weatherInfo.getJSONObject("data");
//
//        String tem = data.getString("temperature");
//
//        String weather=
//        String weaImg;
//        String week;//周
//        String tem;//温度
//        _low_high=tem2+
//                String tem2;//低
//        String tem1;//高
//        String[] win;//
//        String winSpeed
//                String air;//
//        String airLevel
//        String airTips;
//
//        JSONObject hourly = weatherInfo.getJSONObject("hourly");
//        JSONArray data = hourly.getJSONArray("data");
//        JSONObject h01 = data.getJSONObject(0);
//        String humidity = h01.getString("humidity");
//    }

//    public static String getWeatherOfCity(String city) {
//        // 拼接出获取天气数据的URL
//        // https://tianqiapi.com/api?version=v1&appid=36646344&appsecret=c1lgQbP9
//        String weatherUrl = URL_WEATHER + "&city=" + city;
//        Log.d("fan", "----weatherUrl----" + weatherUrl);
//        String weatherResult = getWeather(weatherUrl);
//        Log.d("fan", "----weatherResult----" + weatherResult);
//        return weatherResult;
//    }
}
