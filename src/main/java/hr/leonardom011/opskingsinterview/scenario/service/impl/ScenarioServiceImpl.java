package hr.leonardom011.opskingsinterview.scenario.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.leonardom011.opskingsinterview.exception.ScenarioServiceException;
import hr.leonardom011.opskingsinterview.scenario.model.response.PublicHoliday;
import hr.leonardom011.opskingsinterview.scenario.model.response.WeatherInfo;
import hr.leonardom011.opskingsinterview.scenario.service.ScenarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class ScenarioServiceImpl implements ScenarioService {

    @Value("${api.weatherStats.url}")
    private String weatherStatsApiUrl;
    @Value("${api.publicHoliday.url}")
    private String publicHolidayApiUrl;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public ScenarioServiceImpl(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<WeatherInfo> getWeatherStats() throws JsonProcessingException {
        log.info("Starting HTTP GET {}", weatherStatsApiUrl);
        ResponseEntity<List<WeatherInfo>> response = restTemplate.exchange(
                weatherStatsApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Client error occurred while fetching weather stats");
            throw new ScenarioServiceException(response.getStatusCode(), "Client error occurred while fetching weather stats");
        }
        log.info("Completed HTTP GET {} Response status code: {}", weatherStatsApiUrl, response.getStatusCode());
        return response.getBody();
    }

    @Override
    // Todo: Handle 429 too many requests
    public List<PublicHoliday> getPublicHolidays() {
        log.info("Starting HTTP GET {}", publicHolidayApiUrl);
        ResponseEntity<List<PublicHoliday>> response = restTemplate.exchange(
                publicHolidayApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Client error occurred while fetching public holidays");
            throw new ScenarioServiceException(response.getStatusCode(), "Client error occurred while fetching public holidays");
        }
        log.info("Completed HTTP GET {} Response status code: {}", publicHolidayApiUrl, response.getStatusCode());
        return response.getBody();
    }
}
