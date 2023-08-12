package ru.practicum.shareit.utils.error.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ShareItException {
    public NotFoundException(String message, String details) {
        httpErrorStatus = HttpStatus.NOT_FOUND;
        this.cause = message;
        this.message = details;
    }
}
