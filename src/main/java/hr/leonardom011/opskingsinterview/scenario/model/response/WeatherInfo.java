package hr.leonardom011.opskingsinterview.scenario.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public record WeatherInfo(
        String sky,
        String city,
        LocalDate date,
        @JsonAlias("degrees_in_celsius")
        Integer degreesInCelsius,
        @JsonAlias("times_of_rain_showers")
        String timesOfRainShowers
) {}
