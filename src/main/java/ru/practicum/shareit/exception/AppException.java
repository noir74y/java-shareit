package ru.practicum.shareit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    @Getter
    protected HttpStatus httpErrorStatus;
    protected String cause;
    protected String message;

    public ErrorMessage prepareErrorMessage() {
        return new ErrorMessage(cause, message);
    }

}
