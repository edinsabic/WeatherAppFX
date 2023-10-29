package gh.project.weatherappfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class WeatherControllerFX {
    @FXML
    private TextField cityField;

    @FXML
    private Label welcomeText;

    @FXML
    private VBox rootVBox;

    @FXML
    public Label weatherInfoLabel;

    @FXML
    private VBox vboxContainer;

    @FXML
    protected void onSearchButtonClick() {
        String city = cityField.getText();

        if (city.isEmpty()) {
            welcomeText.setText("Please enter a city.");
            return;
        }

        welcomeText.setText(""); // Clear any previous error message

        // Toggle visibility of labels
        welcomeText.setVisible(false);
        welcomeText.setManaged(false);
        weatherInfoLabel.setVisible(true);

        String weatherData = getWeatherDataForCity(city);
        if (weatherData.equals("Error fetching weather data.")) {
            vboxContainer.getChildren().clear(); // remove chart, if error occurs
        }

        String weatherInfo = parseWeatherInfo(weatherData);

        weatherInfoLabel.setText(weatherInfo);
        weatherInfoLabel.getStyleClass().add("weather-info"); // css part, for font

        // Maximize the window after clicking search
        Stage stage = (Stage) rootVBox.getScene().getWindow();
        stage.setMaximized(true);
    }

    private String getWeatherDataForCity(String city) {
        try {
            return WeatherAPIfx.getWeatherData(city);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return "Error fetching weather data.";
        }
    }

    private void displayHourlyTemperatureChart(List<Double> hourlyTemperatures, JSONArray hourlyTimeArray) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time");
        yAxis.setLabel("Temperature (°C)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Hourly temperatures");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperature");

        for (int i = 0; i < hourlyTimeArray.length(); i++) {
            String time = hourlyTimeArray.getString(i);
            double temperature = hourlyTemperatures.get(i);
            series.getData().add(new XYChart.Data<>(time, temperature));
        }

        lineChart.getData().add(series);

        vboxContainer.getChildren().clear(); // clear existing children from chart container
        vboxContainer.getChildren().add(lineChart); // add LineChart to chart container
    }

    private String parseWeatherInfo(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject current = jsonObject.getJSONObject("current");
            JSONObject daily = jsonObject.getJSONObject("daily");
            JSONArray hourlyTimeArray = jsonObject.getJSONObject("hourly").getJSONArray("time");
            JSONArray hourlyTempArray = jsonObject.getJSONObject("hourly").getJSONArray("temperature_2m");

            double temperatureValue = current.getDouble("temperature_2m");
            double precipitationValue = current.getDouble("precipitation");

            int weatherCodeValue = daily.getJSONArray("weathercode").getInt(0);
            double maxDailyTemp = daily.getJSONArray("temperature_2m_max").getDouble(0);
            double minDailyTemp = daily.getJSONArray("temperature_2m_min").getDouble(0);
            double maxUVindex = daily.getJSONArray("uv_index_max").getDouble(0);

            String sunriseTime = daily.getJSONArray("sunrise").getString(0);
            String sunsetTime = daily.getJSONArray("sunset").getString(0);

            List<Double> hourlyTemperatures = new ArrayList<>();
            StringBuilder hourlyTemperaturesString = new StringBuilder();

            for (int i = 0; i < hourlyTempArray.length(); i++) {
                double temperature = hourlyTempArray.getDouble(i);
                hourlyTemperatures.add(temperature);
                hourlyTemperaturesString.append(String.format("%.1f°C ", temperature));
            }

            displayHourlyTemperatureChart(hourlyTemperatures, hourlyTimeArray);

            return String.format("Current temperature: %.1f °C\nPrecipitation: %.1f\nWeather Code: %d\n" +
                            "Max Temp: %.1f °C\nMin Temp: %.1f °C\nUV Index: %.1f\nSunrise: %s\nSunset: %s",
                    temperatureValue, precipitationValue, weatherCodeValue,
                    maxDailyTemp, minDailyTemp, maxUVindex, sunriseTime, sunsetTime);
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }
}