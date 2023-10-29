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

        try {
            String weatherData = getWeatherDataForCity(city);
            String weatherInfo = parseWeatherInfo(weatherData);

            welcomeText.setText("Weather information for " + city + ":\n" + weatherInfo);
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching weather data.");
        }
    }

    private String getWeatherDataForCity(String city) throws IOException {
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=46.0511&longitude=14.5051&current=temperature_2m&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max&timezone=Europe%2FBerlin";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        return content.toString();
    }

    private String parseWeatherInfo(String json) {
        System.out.println(json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject current = jsonObject.getJSONObject("current");
            JSONObject daily = jsonObject.getJSONObject("daily");

            double temperatureValue = current.getDouble("temperature_2m");

            int weatherCodeValue = daily.getJSONArray("weathercode").getInt(0);
            double maxDailyTemp = daily.getJSONArray("temperature_2m_max").getDouble(0);
            double minDailyTemp = daily.getJSONArray("temperature_2m_min").getDouble(0);
            double maxUVindex = daily.getJSONArray("uv_index_max").getDouble(0);
            String sunriseTime = daily.getJSONArray("sunrise").getString(0);
            String sunsetTime = daily.getJSONArray("sunset").getString(0);

            return String.format("Current temperature: %.1f °C\nWeather Code: %d\nMax Temp: %.1f °C\nMin Temp: %.1f °C\nUV Index: %.1f\nSunrise: %s\nSunset: %s",
                    temperatureValue, weatherCodeValue, maxDailyTemp, minDailyTemp, maxUVindex, sunriseTime, sunsetTime);
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }
}