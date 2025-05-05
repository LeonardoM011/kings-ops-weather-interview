package hr.leonardom011.opskingsinterview.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record WeatherDTO(
        String sky,
        String city,
        LocalDate date,
        Integer degrees,
        @JsonAlias("is_public_holiday")
        Boolean isPublicHoliday,
        @JsonAlias("times_of_rain_showers")
        List<LocalTime> timesOfRainShowers
) {}
