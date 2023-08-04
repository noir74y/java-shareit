package ru.practicum.shareit.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    public static final String HEADER_USER_ID = "X-Sharer-User-Id";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    public static final String OFFSET_DEFAULT = "0";
    public static final String PAGE_SIZE_MAX = "999";

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}