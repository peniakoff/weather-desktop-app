package pl.tomaszmiller.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Label weatherText;

    @FXML
    private ProgressIndicator progressIndicator;

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
                progressIndicator.setVisible(true);
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
        weatherText.setText("Temperatura dla " + weatherInfo.getCityName() + ": " + decimalFormat.format(weatherInfo.getCelsius()) + " °C\n" +
                "Temperatura dla " + weatherInfo.getCityName() + ": " + decimalFormat.format(weatherInfo.getFahrenheit()) + " °F\n" +
                "Ciśnienie dla " + weatherInfo.getCityName() + ": " + weatherInfo.getPressure() + " hPa\n" +
                "Wilgotność dla " + weatherInfo.getCityName() + ": " + weatherInfo.getHumidity() + "%\n" +
                "Zachmurzenie dla " + weatherInfo.getCityName() + ": " + weatherInfo.getCloudAll());
        progressIndicator.setVisible(false);
    }

}