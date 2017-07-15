package pl.tomaszmiller.models.services;

/**
 * Created by Peniakoff on 15.07.2017.
 */
public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();

    public static WeatherService getInstance() {
        return ourInstance;
    }

    private WeatherService() {
    }
}
