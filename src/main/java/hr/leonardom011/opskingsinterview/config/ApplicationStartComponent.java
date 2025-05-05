package hr.leonardom011.opskingsinterview.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.leonardom011.opskingsinterview.weather.model.dto.WeatherDTO;
import hr.leonardom011.opskingsinterview.weather.service.WeatherService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ApplicationStartComponent {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    public ApplicationStartComponent(WeatherService weatherService, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        List<WeatherDTO> weatherDTO = weatherService.getWeatherStats();

        log.info(weatherDTO.toString());
        log.info(weatherService.getTemperatureStats(weatherDTO).toString());
        log.info(weatherService.getRainStats(weatherDTO).toString());
        log.info(weatherService.getSkyStats(weatherDTO).toString());
        log.info(weatherService.getPublicHolidaySkyStats(weatherDTO).toString());


        //log.info(objectMapper.writeValueAsString(scenarioService.getWeatherStats()));

    }

}
