package gh.project.weatherappfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPIfx {
    public static String getWeatherData(String city) throws IOException, IllegalArgumentException {
        String apiUrl = generateApiUrl(city);
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

    private static String generateApiUrl(String city) throws IllegalArgumentException {
        String coordinates = WeatherControllerFX.CITY_COORDINATES.get(city);
        if (coordinates == null) {
            throw new IllegalArgumentException("Invalid or non-existent city input!");
        }

        return "https://api.open-meteo.com/v1/forecast?" + coordinates +
                "&current=temperature_2m,precipitation" +
                "&hourly=temperature_2m" +
                "&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max" +
                "&timezone=Europe%2FBerlin" +
                "&forecast_days=1";
    }
}
