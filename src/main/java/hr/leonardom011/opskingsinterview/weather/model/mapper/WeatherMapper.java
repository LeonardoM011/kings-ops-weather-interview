package hr.leonardom011.opskingsinterview.weather.model.mapper;

import hr.leonardom011.opskingsinterview.exception.WeatherServiceException;
import hr.leonardom011.opskingsinterview.scenario.model.response.PublicHolidayInfo;
import hr.leonardom011.opskingsinterview.scenario.model.response.WeatherInfo;
import hr.leonardom011.opskingsinterview.weather.model.dto.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
public class WeatherMapper {

    public List<WeatherDTO> convertToWeatherDTO(List<WeatherInfo> weatherStats, List<PublicHolidayInfo> publicHolidays) {
        return
                weatherStats.stream().map(weatherInfo -> new WeatherDTO(
                        weatherInfo.sky(),
                        weatherInfo.city(),
                        weatherInfo.date(),
                        weatherInfo.degreesInCelsius(),
                        findIfPublicHoliday(weatherInfo.date(), publicHolidays),
                        weatherInfo.timesOfRainShowers() != null ? convertStringToLocalTime(weatherInfo.timesOfRainShowers()) : null
                )).toList();
    }

    public List<LocalTime> convertStringToLocalTime(String times) {
        return Stream.of(times.replaceAll("\\s+", "").split(","))
                .map(LocalTime::parse)
                .toList();
    }

    private Boolean findIfPublicHoliday(LocalDate weatherDate, List<PublicHolidayInfo> publicHolidays) {
        String str = publicHolidays.stream()
                .filter(publicHolidayInfo -> publicHolidayInfo.date().equals(weatherDate))
                .findAny()
                .orElseThrow(() -> {
                    log.error("Public holiday not found for date: {}", weatherDate);
                    return new WeatherServiceException(HttpStatus.BAD_REQUEST, "Public holiday not found");
                })
                .isPublicHoliday();
        if (str.equalsIgnoreCase("YES")) {
            return true;
        } else if (str.equalsIgnoreCase("NO")) {
            return false;
        } else {
            log.error("Invalid public holiday value: {}", str);
            throw new WeatherServiceException(HttpStatus.BAD_REQUEST, "Invalid public holiday value");
        }
    }
}
