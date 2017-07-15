package pl.tomaszmiller.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import pl.tomaszmiller.models.services.WeatherInfo;
import pl.tomaszmiller.models.services.WeatherObserver;
import pl.tomaszmiller.models.services.WeatherService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainController implements Initializable, WeatherObserver {

    @FXML
    private TextField cityName;

    @FXML
    private Button showWeather;

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private WeatherService weatherService = WeatherService.getService();

    public void initialize(URL location, ResourceBundle resources) {
        weatherService.addWeatherObserver(this);
        showWeather.setOnMouseClicked(e -> {
            if (!cityName.getText().isEmpty() && cityName.getText().length() > 3) {
                weatherService.makeCall(cityName.getText(), "pl");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Wrong city's name!");
                alert.show();
            }
        });
    }

    @Override
    public void updateWeather(WeatherInfo weatherInfo) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        System.out.println("Temperatura dla " + weatherInfo.getCityName() + ": " + decimalFormat.format(weatherInfo.getCelsius()) + " °C");
        System.out.println("Temperatura dla " + weatherInfo.getCityName() + ": " + decimalFormat.format(weatherInfo.getFahrenheit()) + " °F");
        System.out.println("Ciśnienie dla " + weatherInfo.getCityName() + ": " + weatherInfo.getPressure() + " hPa");
        System.out.println("Wilgotność dla " + weatherInfo.getCityName() + ": " + weatherInfo.getHumidity() + "%");
        System.out.println("Zachmurzenie dla " + weatherInfo.getCityName() + ": " + weatherInfo.getCloudAll());
    }

}