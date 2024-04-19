import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherInfo {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter a city or town in India: ");
            String city = reader.readLine();
            String weatherInfo = getWeatherInfo(city);
            System.out.println("Weather Temperature: " + weatherInfo);

            String fact = getCityFact(city);
            System.out.println("Fact about " + city + ": " + fact);
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
    }

    public static String getWeatherInfo(String city) {
        try {
            String apiKey = "YOUR_API_KEY"; // Replace with your actual API key
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",IN&appid=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JSONObject jsonResponse = (JSONObject) new JSONParser().parse(response.toString());
            JSONObject main = (JSONObject) jsonResponse.get("main");
            double tempKelvin = Double.parseDouble(main.get("temp").toString());
            double tempCelsius = tempKelvin - 273.15;
            return String.format("%.2f°C", tempCelsius);
        } catch (IOException | ParseException e) {
            return "Weather information not available";
        }
    }

    public static String getCityFact(String city) {
        // You can add facts about various cities here or fetch them from an external API or database
        if (city.equalsIgnoreCase("Mumbai")) {
            return "Mumbai is also known as the 'City of Dreams' due to its numerous opportunities and vibrant culture.";
        } else if (city.equalsIgnoreCase("Delhi")) {
            return "Delhi is home to the famous Red Fort, a UNESCO World Heritage Site.";
        } else if (city.equalsIgnoreCase("Kolkata")) {
            return "Kolkata is known for its rich literary heritage and is often called the 'Cultural Capital of India.'";
        } else {
            return "Fact about " + city + " not available";
        }
    }
}

