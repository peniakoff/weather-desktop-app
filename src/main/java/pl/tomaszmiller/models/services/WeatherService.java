package pl.tomaszmiller.models.services;

import javafx.application.Platform;
import org.json.JSONObject;
import pl.tomaszmiller.models.Config;
import pl.tomaszmiller.models.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Peniakoff on 15.07.2017.
 */
public class WeatherService {

    private String appUrl;
    private double temp;
    private int pressure;
    private int humidity;
    private int cloudAll;
    private String cityName;

    List<WeatherObserver> observerList = new ArrayList<>();

    private ExecutorService executorService;

    private static WeatherService ourInstance = new WeatherService();

    public static WeatherService getService() {
        return ourInstance;
    }

    private WeatherService() {
        executorService = Executors.newFixedThreadPool(2);
        appUrl = "";
    }

    public void makeCall(String city, String country) {
        executorService.execute(() -> {
            appUrl = Config.APPURL + "weather?q=" + city + "," + country + "&appid=" + Config.APPID;
            cityName = city;
            parseJsonData(Utils.connectAndResponse(appUrl), city);
        });
    }

    private synchronized void parseJsonData(String data, String city) {
        JSONObject jsonObject = new JSONObject(data);
        JSONObject object = jsonObject.getJSONObject("main");
        JSONObject clouds = jsonObject.getJSONObject("clouds");
        temp = object.getDouble("temp");
        pressure = object.getInt("pressure");
        humidity = object.getInt("humidity");
        cloudAll = clouds.getInt("all");
        cityName = city;
        Platform.runLater(() -> informObservers()); //only main thread for GUI edit
    }

    public void addWeatherObserver(WeatherObserver observer) {
        observerList.add(observer);
    }

    public void removeWeatherObserver(WeatherObserver observer) {
        observerList.remove(observer);
    }

    private void informObservers() {
        WeatherInfo weatherInfo = new WeatherInfo(temp, pressure, humidity, cloudAll, cityName);
        observerList.forEach(s -> {
            s.updateWeather(weatherInfo);
        });
    }

}
