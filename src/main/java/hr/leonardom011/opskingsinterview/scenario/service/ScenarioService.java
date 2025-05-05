package hr.leonardom011.opskingsinterview.scenario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import hr.leonardom011.opskingsinterview.scenario.model.response.PublicHolidayInfo;
import hr.leonardom011.opskingsinterview.scenario.model.response.WeatherInfo;

import java.util.List;

public interface ScenarioService {

    List<WeatherInfo> getWeatherStats();
    List<PublicHolidayInfo> getPublicHolidays();

}
