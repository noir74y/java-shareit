package ru.practicum.shareit.utils;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.practicum.shareit.utils.AppConfiguration.LOCAL_DATETIME_SERIALIZER;

@Configuration
public class LocalDateTimeFormatterConfig {
    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATETIME_SERIALIZER);
        return module;
    }
}
