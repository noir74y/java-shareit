package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;

public class OtherException extends AppException {
    public OtherException(Exception exception) {
        httpErrorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        cause = exception.getCause().getMessage();
        message = exception.getMessage();
    }
}
