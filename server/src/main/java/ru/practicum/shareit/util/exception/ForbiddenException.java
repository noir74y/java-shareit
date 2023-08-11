package ru.practicum.shareit.util.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ShareItException {
    public ForbiddenException(String message, String details) {
        httpErrorStatus = HttpStatus.FORBIDDEN;
        this.cause = message;
        this.message = details;
    }
}
