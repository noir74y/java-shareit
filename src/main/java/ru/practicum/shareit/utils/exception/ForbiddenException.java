package ru.practicum.shareit.utils.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AppException {
    public ForbiddenException(String message, String details) {
        httpErrorStatus = HttpStatus.FORBIDDEN;
        this.cause = message;
        this.message = details;
    }
}
