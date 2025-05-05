package hr.leonardom011.opskingsinterview.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.leonardom011.opskingsinterview.email.service.EmailService;
import hr.leonardom011.opskingsinterview.weather.model.dto.WeatherDTO;
import hr.leonardom011.opskingsinterview.weather.model.response.TemperatureStats;
import hr.leonardom011.opskingsinterview.weather.service.WeatherService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ApplicationStartComponent {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${mail.recipient}")
    private String mailTo;

    private final String emailTemplate = """
            ### Github URL: https://github.com/LeonardoM011/kings-ops-weather-interview ###
            
            Hi,
            Here are your San Francisco weather stats for 2022-11:
            The max temperature was: {{maxTemp}}
            The avg temperature was: {{avgTemp}}
            The min temperature was: {{minTemp}}
            
            Overview of unique "sky" values and their counts:
            {{skyStats}}
            Rain showers:
            {{rainStats}}
            "Sky" statuses during holidays:
            {{skyStatsPublicHolidays}}
            Have a nice day!
            """;

    public ApplicationStartComponent(WeatherService weatherService, ObjectMapper objectMapper, EmailService emailService) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() throws MessagingException, IOException {
        List<WeatherDTO> weatherDTO = weatherService.getWeatherStats();
        log.info(weatherDTO.toString());

        TemperatureStats tempStats = weatherService.getTemperatureStats(weatherDTO);
        log.info(tempStats.toString());

        MultiValueMap<LocalDate, LocalTime> rainStats = weatherService.getRainStats(weatherDTO);
        log.info(rainStats.toString());

        Map<String, Integer> skyStats = weatherService.getSkyStats(weatherDTO);
        log.info(skyStats.toString());

        Map<LocalDate, String> skyStatsPublicHolidays = weatherService.getPublicHolidaySkyStats(weatherDTO);
        log.info(skyStatsPublicHolidays.toString());

        String emailBody = formatEmailBody(tempStats, skyStats, rainStats, skyStatsPublicHolidays);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String mailTitle = "Certification Level 4 | Practical Challenge | ".concat(mailFrom).concat(" | ").concat(LocalDateTime.now().format(dateTimeFormatter));

        emailService.sendNewMail(mailTo, mailTitle, emailBody, objectMapper.writeValueAsString(weatherDTO));

    }

    private String formatEmailBody(TemperatureStats temperatureStats, Map<String, Integer> skyStats, MultiValueMap<LocalDate, LocalTime> rainStats, Map<LocalDate, String> skyStatsPublicHolidays) {
        return emailTemplate
                .replace("{{maxTemp}}", temperatureStats.maxTemperature().toString())
                .replace("{{avgTemp}}", temperatureStats.avgTemperature().toString())
                .replace("{{minTemp}}", temperatureStats.minTemperature().toString())
                .replace("{{skyStats}}", formatSkyStats(skyStats))
                .replace("{{rainStats}}", formatRainStats(rainStats))
                .replace("{{skyStatsPublicHolidays}}", formatSkyStatsPublicHolidays(skyStatsPublicHolidays));
    }

    private String formatSkyStats(Map<String, Integer> skyStats) {
        String str = "";
        for (Map.Entry<String, Integer> entry : skyStats.entrySet()) {
            str = str.concat(firstCapitalLetter(entry.getKey()) + ": " + entry.getValue() + "\n");
        }
        return str;
    }

    private String firstCapitalLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String formatRainStats(MultiValueMap<LocalDate, LocalTime> rainStats) {
        String str = "";
        for (Map.Entry<LocalDate, List<LocalTime>> entry : rainStats.entrySet()) {
            for (LocalTime time : entry.getValue()) {
                str = str.concat(entry.getKey() + ": " + time + "\n");
            }
        }
        return str;
    }

    private String formatSkyStatsPublicHolidays(Map<LocalDate, String> skyStatsPublicHolidays) {
        String str = "";
        for (Map.Entry<LocalDate, String> entry : skyStatsPublicHolidays.entrySet()) {
            str = str.concat(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return str;
    }

}
