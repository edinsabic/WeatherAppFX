package gh.project.weatherappfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.ClosedFileSystemException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherControllerFX {
    @FXML
    private TextField cityField;

    @FXML
    private Label welcomeText;

    @FXML
    private Label weatherInfoLabel;

    @FXML
    protected void onSearchButtonClick() {
        String city = cityField.getText();
        welcomeText.setText("Searching for weather in " + city + "...");

        String weatherData = getWeatherDataForCity(city);
        String weatherInfo = parseWeatherInfo(weatherData);
        welcomeText.setText("Weather information for " + city + ":\n" + weatherInfo);
    }

    private String getWeatherDataForCity(String city) {
        try {
            return WeatherAPIfx.getWeatherData(city);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return "Error fetching weather data.";
        }
    }

    private String parseWeatherInfo(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject current = jsonObject.getJSONObject("current");
            JSONObject daily = jsonObject.getJSONObject("daily");

            double temperatureValue = current.getDouble("temperature_2m");
            double precipitationValue = current.getDouble("precipitation");

            int weatherCodeValue = daily.getJSONArray("weathercode").getInt(0);
            double maxDailyTemp = daily.getJSONArray("temperature_2m_max").getDouble(0);
            double minDailyTemp = daily.getJSONArray("temperature_2m_min").getDouble(0);
            double maxUVindex = daily.getJSONArray("uv_index_max").getDouble(0);

            String sunriseTime = daily.getJSONArray("sunrise").getString(0);
            String sunsetTime = daily.getJSONArray("sunset").getString(0);

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