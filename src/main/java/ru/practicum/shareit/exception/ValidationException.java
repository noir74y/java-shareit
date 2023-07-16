package ru.practicum.shareit.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class ValidationException extends AppException {

    public ValidationException() {
        httpErrorStatus = HttpStatus.BAD_REQUEST;
    }

    public ValidationException(Exception exception) {
        this();
        cause = "ошибка валидации";
        message = ((MethodArgumentNotValidException) exception)
                .getBindingResult()
                .getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }

    public ValidationException(String cause, String message) {
        this();
        this.cause = cause;
        this.message = message;
    }
}
