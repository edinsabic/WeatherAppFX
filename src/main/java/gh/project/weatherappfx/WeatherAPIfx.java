package gh.project.weatherappfx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherAPIfx {
    private static final Map<String, String> CITY_COORDINATES = new HashMap<>();

    static {
        CITY_COORDINATES.put("Ljubljana", "latitude=46.0511&longitude=14.5051");
        CITY_COORDINATES.put("Tokyo", "latitude=35.6762&longitude=139.6503");
        CITY_COORDINATES.put("Los Angeles", "latitude=34.0522&longitude=-118.2437");
        CITY_COORDINATES.put("Delhi", "latitude=28.6139&longitude=77.2090");
        CITY_COORDINATES.put("Shanghai", "latitude=31.2304&longitude=121.4737");
        CITY_COORDINATES.put("Beijing", "latitude=39.9042&longitude=116.4074");
        CITY_COORDINATES.put("Mumbai", "latitude=19.0760&longitude=72.8777");
        CITY_COORDINATES.put("Istanbul", "latitude=41.0082&longitude=28.9784");
        CITY_COORDINATES.put("Lahore", "latitude=31.5497&longitude=74.3436");
        CITY_COORDINATES.put("Paris", "latitude=48.8566&longitude=2.3522");
        CITY_COORDINATES.put("Lima", "latitude=-12.0464&longitude=-77.0428");
        CITY_COORDINATES.put("Bangkok", "latitude=13.7563&longitude=100.5018");
        CITY_COORDINATES.put("New York", "latitude=40.7128&longitude=-74.0060");
        CITY_COORDINATES.put("London", "latitude=51.5074&longitude=-0.1278");
        CITY_COORDINATES.put("Sao Paulo", "latitude=-23.5505&longitude=-46.6333");
        CITY_COORDINATES.put("Mexico City", "latitude=19.4326&longitude=-99.1332");
        CITY_COORDINATES.put("Cairo", "latitude=30.0444&longitude=31.2357");
        CITY_COORDINATES.put("Osaka", "latitude=34.6937&longitude=135.5023");
        CITY_COORDINATES.put("Rio de Janeiro", "latitude=-22.9068&longitude=-43.1729");
        CITY_COORDINATES.put("Jakarta", "latitude=-6.2088&longitude=106.8456");
        CITY_COORDINATES.put("Karachi", "latitude=24.8607&longitude=67.0011");
        CITY_COORDINATES.put("Manila", "latitude=14.5995&longitude=120.9842");
        CITY_COORDINATES.put("Lagos", "latitude=6.5244&longitude=3.3792");
        CITY_COORDINATES.put("Kolkata", "latitude=22.5726&longitude=88.3639");
        CITY_COORDINATES.put("Chennai", "latitude=13.0827&longitude=80.2707");
        CITY_COORDINATES.put("Bangalore", "latitude=12.9716&longitude=77.5946");
        CITY_COORDINATES.put("Houston", "latitude=29.7604&longitude=-95.3698");
        CITY_COORDINATES.put("Berlin", "latitude=52.5200&longitude=13.4050");
        CITY_COORDINATES.put("Madrid", "latitude=40.4168&longitude=-3.7038");
        CITY_COORDINATES.put("Toronto", "latitude=43.6519&longitude=-79.3817");
        CITY_COORDINATES.put("Melbourne", "latitude=-37.8136&longitude=144.9631");
        CITY_COORDINATES.put("Sydney", "latitude=-33.8688&longitude=151.2093");
        CITY_COORDINATES.put("Moscow", "latitude=55.7558&longitude=37.6176");
        CITY_COORDINATES.put("Rome", "latitude=41.9028&longitude=12.4964");
        CITY_COORDINATES.put("Kiev", "latitude=50.4501&longitude=30.5234");
        CITY_COORDINATES.put("Minsk", "latitude=53.9045&longitude=27.5615");
        CITY_COORDINATES.put("Vienna", "latitude=48.2082&longitude=16.3738");
        CITY_COORDINATES.put("Barcelona", "latitude=41.3851&longitude=2.1734");
        CITY_COORDINATES.put("Maribor", "latitude=46.5547&longitude=15.6467");
        CITY_COORDINATES.put("Koper", "latitude=45.5469&longitude=13.7294");
        CITY_COORDINATES.put("Velenje", "latitude=46.3592&longitude=15.1103");
        CITY_COORDINATES.put("Kranj", "latitude=46.2389&longitude=14.3556");
        CITY_COORDINATES.put("Bled", "latitude=46.3692&longitude=14.1136");
        CITY_COORDINATES.put("Novo Mesto", "latitude=45.804&longitude=15.1689");
    }

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
        String coordinates = CITY_COORDINATES.get(city);
        if (coordinates == null) {
            throw new IllegalArgumentException("Invalid or non-existent city input!");
        }

        return "https://api.open-meteo.com/v1/forecast?" + coordinates +
                "&current=temperature_2m,precipitation" +
                "&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max" +
                "&timezone=Europe%2FBerlin";
    }
}
