package gh.project.weatherappfx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.Map;

public class WeatherControllerFX {
    @FXML
    private TextField cityField;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private Label welcomeText;

    @FXML
    private VBox rootVBox;

    @FXML
    public Label weatherInfoLabel;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ImageView weatherIcon;

    public static final Map<String, String> CITY_COORDINATES = new HashMap<>();
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

    private void setWeatherIcon(int weatherCodeValue) {
        String iconPath = "";

        switch (weatherCodeValue) {
            case 0:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/sunny.png";
                break;
            case 45:
            case 48:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/cloudy.png";
                break;
            case 66:
            case 67:
            case 71:
            case 73:
            case 75:
            case 77:
            case 85:
            case 86:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/snow.png";
                break;
            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
            case 61:
            case 63:
            case 65:
            case 80:
            case 81:
            case 82:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/cloudy.png";
                break;
            case 1:
            case 2:
            case 3:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/cloudy_sunny.png";
                break;
            case 95:
            case 96:
            case 99:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/heavy_rain.png";
                break;
            default:
                iconPath += "src/main/resources/gh/project/weatherappfx/weather-icons/default.png";
                break;
        }

        Image image = new Image(new File(iconPath).toURI().toString());
        weatherIcon.setImage(image);
    }

    public void initialize() {
        List<String> cities = new ArrayList<>(CITY_COORDINATES.keySet());
        cityComboBox.setItems(FXCollections.observableArrayList(cities));

        cityComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cityField.setText(newValue);
            }
        });
    }

    @FXML
    protected void onSearchButtonClick() {
        String selectedCity = cityComboBox.getValue();

        if (selectedCity == null || selectedCity.isEmpty()) {
            welcomeText.setText("Please select a city.");
            return;
        }

        welcomeText.setText(""); // Clear any previous error message

        welcomeText.setVisible(false);
        welcomeText.setManaged(false);
        weatherInfoLabel.setVisible(true);

        String weatherData = getWeatherDataForCity(selectedCity);
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
            setWeatherIcon(weatherCodeValue);
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