package ru.practicum.shareit.utils;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.format.DateTimeFormatter;

public class AppConfiguration {
    public static final String HEADER_USER_ID = "X-Sharer-User-Id";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    public static final String OFFSET_DEFAULT = "0";
    public static final String PAGE_SIZE_MAX = "999";
}