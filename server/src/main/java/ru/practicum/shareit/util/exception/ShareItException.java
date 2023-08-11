package ru.practicum.shareit.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.shareit.utils.exception.ErrorMessage;


public class ShareItException extends RuntimeException {
    @Getter
    protected HttpStatus httpErrorStatus;
    protected String cause;
    protected String message;

    public ErrorMessage prepareErrorMessage() {
        return new ErrorMessage(cause, message);
    }

}
