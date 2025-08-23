package com.example.orderservice.api.dto.mapper;



import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class OffsetDateTimeMapper {
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String mapOffsetDateTimeToString(OffsetDateTime offsetDateTime) {
        return Optional.ofNullable(offsetDateTime)
                .map(odt -> offsetDateTime.atZoneSameInstant(ZoneOffset.systemDefault()))
                .map(odt -> odt.format(DATE_FORMAT))
                .orElse(null);
    }

    public OffsetDateTime mapStringToOffsetDateTime(String dateString) {
        return Optional.ofNullable(dateString)
                .map(str -> OffsetDateTime.parse(str, DATE_FORMAT))
                .orElse(null);
    }
}
