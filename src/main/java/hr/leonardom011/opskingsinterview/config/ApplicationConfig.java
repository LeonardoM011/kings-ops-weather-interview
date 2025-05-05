package hr.leonardom011.opskingsinterview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"hr.leonardom011"})
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Weather response is not in JSON format but octet-stream
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(
                Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM}));

        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
    }

}
