package gh.project.weatherappfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherControllerFX {
    @FXML
    private TextField cityField;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onSearchButtonClick() {
        String city = cityField.getText();
        welcomeText.setText("Searching for weather in " + city + "...");

        try {
            String weatherData = getWeatherDataForCity(city);
            String currentTemperature = parseCurrentTemperature(weatherData);

            welcomeText.setText("Current temperature in " + city + ": " + currentTemperature + " °C");
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching weather data.");
        }
    }

    private String getWeatherDataForCity(String city) throws IOException {
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=46.0511&longitude=14.5051&current=temperature_2m";
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

    private String parseCurrentTemperature(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject current = jsonObject.getJSONObject("current");
            double temperatureValue = current.getDouble("temperature_2m");
            return String.format("%.1f", temperatureValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return "N/A";
        }
    }
}
