package com.valo.journalApp.service;

import com.valo.journalApp.api.response.WeatherResponse;
import com.valo.journalApp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "4a1f3d48f91ee92ac0c171dc1c89c74c";
    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    // get call through java (restTemplate)
    public WeatherResponse getWeather(String city) {
        String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
        // json to POJO (deserialize) here through WeatherResponse.class
        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

    // post call through java (restTemplate), will not work as finalApi doesn't support post but can replace the api to work
    public WeatherResponse postWeather(String city) {
        String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
        // dummy reqBody
//        String reqBody = "";
//        HttpEntity<String> httpEntity = new HttpEntity<>(reqBody);

        // also you can add headers too
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key", "value");
        UserEntity reqBody = UserEntity.builder().username("jacky").password("chan").build();
        HttpEntity<UserEntity> httpEntity = new HttpEntity<>(reqBody, httpHeaders); // overloading

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalApi, HttpMethod.POST, httpEntity, WeatherResponse.class);
        return response.getBody();
    }

}
