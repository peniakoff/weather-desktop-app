package pl.tomaszmiller.models.services;

/**
 * Created by Peniakoff on 15.07.2017.
 */
public class WeatherInfo {
    private double temp;
    private int pressure;
    private int humidity;
    private int cloudAll;
    private String cityName;

    public WeatherInfo(double temp, int pressure, int humidity, int cloudAll, String cityName) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudAll = cloudAll;
        this.cityName = cityName;
    }

    public WeatherInfo() {
    }

    public double getTemp() {
        return temp;
    }

    public double getCelsius() {
        return temp - 273.15;
    }

    public double getFahrenheit() {
        return temp * 9/5 - 459.67;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloudAll() {
        return cloudAll;
    }

    public String getCityName() {
        return cityName;
    }
}
