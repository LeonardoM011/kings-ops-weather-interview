package hr.leonardom011.opskingsinterview.weather.model.response;

public record TemperatureStats(
        Integer minTemperature,
        Integer maxTemperature,
        Integer avgTemperature
) {}
