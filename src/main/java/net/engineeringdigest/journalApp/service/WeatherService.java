package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
 public class WeatherService {
        private static final String apiKey = "44b3d2b6d9421d40e7493bfa4455f01a";

        private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private RedisService redisService;

        public WeatherResponse getWeather(String city) {
            WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
            if (weatherResponse != null) {
                return weatherResponse;
            }else{
                String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
                ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
                WeatherResponse body = response.getBody();
                if (body != null) {
                    redisService.set("weather_of_" + city, body, 600l);
                }
                return body;
            }

        }
    }


