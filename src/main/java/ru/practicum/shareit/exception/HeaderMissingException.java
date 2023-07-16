package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;

public class HeaderMissingException extends AppException {
    public HeaderMissingException(Exception exception) {
        httpErrorStatus = HttpStatus.BAD_REQUEST;
        this.cause = "HTTP header is missing";
        this.message = ((MissingRequestHeaderException) exception).getHeaderName();
    }
}

