package hr.leonardom011.opskingsinterview.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.leonardom011.opskingsinterview.scenario.service.ScenarioService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStartComponent {

    private final ScenarioService scenarioService;
    private final ObjectMapper objectMapper;

    public ApplicationStartComponent(ScenarioService scenarioService, ObjectMapper objectMapper) {
        this.scenarioService = scenarioService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        log.info(scenarioService.getWeatherStats().getFirst().toString());
        //log.info(objectMapper.writeValueAsString(scenarioService.getWeatherStats()));

    }

}
