package hr.leonardom011.opskingsinterview.weather.service.impl;

import hr.leonardom011.opskingsinterview.scenario.service.ScenarioService;
import hr.leonardom011.opskingsinterview.weather.model.dto.WeatherDTO;
import hr.leonardom011.opskingsinterview.weather.model.mapper.WeatherMapper;
import hr.leonardom011.opskingsinterview.weather.model.response.TemperatureStats;
import hr.leonardom011.opskingsinterview.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final ScenarioService scenarioService;
    private final WeatherMapper weatherMapper;

    public WeatherServiceImpl(ScenarioService scenarioService, WeatherMapper weatherMapper) {
        this.scenarioService = scenarioService;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public List<WeatherDTO> getWeatherStats() {
        return weatherMapper.convertToWeatherDTO(
                scenarioService.getWeatherStats(),
                scenarioService.getPublicHolidays()
        );
    }

    @Override
    public TemperatureStats getTemperatureStats(List<WeatherDTO> weatherStats) {
        Integer maxTemp = null;
        Integer minTemp = null;
        Integer avgTemp = 0;
        for (WeatherDTO weatherDTO : weatherStats) {
            Integer temp = weatherDTO.degrees();
            avgTemp += temp;
            if (maxTemp == null || temp > maxTemp) {
                maxTemp = temp;
            }
            if (minTemp == null || temp < minTemp) {
                minTemp = temp;
            }
        }
        avgTemp = avgTemp / weatherStats.size();
        return new TemperatureStats(minTemp, maxTemp, avgTemp);
    }

    @Override
    public Map<String, Integer> getSkyStats(List<WeatherDTO> weatherStats) {
        Map<String, Integer> skyStats = new TreeMap<>();
        for (WeatherDTO weatherDTO : weatherStats) {
            String sky = weatherDTO.sky();
            skyStats.put(sky, skyStats.getOrDefault(sky, 0) + 1);
        }
        return skyStats;
    }

    @Override
    public MultiValueMap<LocalDate, LocalTime> getRainStats(List<WeatherDTO> weatherStats) {
        MultiValueMap<LocalDate, LocalTime> rainStats = new LinkedMultiValueMap<>();
        for (WeatherDTO weatherDTO : weatherStats) {
            if (weatherDTO.timesOfRainShowers() == null || weatherDTO.timesOfRainShowers().isEmpty()) {
                continue;
            }
            List<LocalTime> rainTimes = new ArrayList<>(weatherDTO.timesOfRainShowers());
            Collections.sort(rainTimes);
            for (LocalTime time : rainTimes) {
                rainStats.add(weatherDTO.date(), time);
            }
        }
        return rainStats;
    }

    @Override
    public Map<LocalDate, String> getPublicHolidaySkyStats(List<WeatherDTO> weatherStats) {
        Map<LocalDate, String> publicHolidaySkyStats = new TreeMap<>();
        for (WeatherDTO weatherDTO : weatherStats) {
            if (weatherDTO.isPublicHoliday()) {
                publicHolidaySkyStats.put(weatherDTO.date(), weatherDTO.sky());
            }
        }
        return publicHolidaySkyStats;
    }
}
