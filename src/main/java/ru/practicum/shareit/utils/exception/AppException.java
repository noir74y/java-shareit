package ru.practicum.shareit.utils.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import ru.practicum.shareit.utils.exception.handler.ErrorMessage;


public class AppException extends RuntimeException {
    @Getter
    protected HttpStatus httpErrorStatus;
    protected String cause;
    protected String message;

    public ErrorMessage prepareErrorMessage() {
        return new ErrorMessage(cause, message);
    }

}
