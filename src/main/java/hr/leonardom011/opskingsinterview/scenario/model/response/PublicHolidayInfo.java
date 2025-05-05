package hr.leonardom011.opskingsinterview.scenario.model.response;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public record PublicHolidayInfo(
        LocalDate date,
        @JsonAlias("is_public_holiday")
        String isPublicHoliday
) {}
