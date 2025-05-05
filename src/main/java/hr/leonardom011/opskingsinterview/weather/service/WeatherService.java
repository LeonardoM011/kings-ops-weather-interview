package hr.leonardom011.opskingsinterview.weather.service;

import hr.leonardom011.opskingsinterview.weather.model.dto.WeatherDTO;
import hr.leonardom011.opskingsinterview.weather.model.response.TemperatureStats;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface WeatherService {

    List<WeatherDTO> getWeatherStats();

    TemperatureStats getTemperatureStats(List<WeatherDTO> weatherStats);

    Map<String, Integer> getSkyStats(List<WeatherDTO> weatherStats);

    MultiValueMap<LocalDate, LocalTime> getRainStats(List<WeatherDTO> weatherStats);

    Map<LocalDate, String> getPublicHolidaySkyStats(List<WeatherDTO> weatherStats);
}
