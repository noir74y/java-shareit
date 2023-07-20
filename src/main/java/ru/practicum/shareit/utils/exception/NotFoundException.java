package ru.practicum.shareit.utils.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {
    public NotFoundException(String message, String details) {
        httpErrorStatus = HttpStatus.NOT_FOUND;
        this.cause = message;
        this.message = details;
    }
}
