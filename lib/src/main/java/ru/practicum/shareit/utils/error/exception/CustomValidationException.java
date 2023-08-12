package ru.practicum.shareit.utils.error.exception;

import org.springframework.http.HttpStatus;

public class CustomValidationException extends ShareItException {
    public CustomValidationException(String message, String details) {
        httpErrorStatus = HttpStatus.BAD_REQUEST;
        this.cause = message;
        this.message = details;
    }
}

