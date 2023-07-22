package ru.practicum.shareit.utils.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

public class GenericValidationException extends AppException {
    public GenericValidationException(Exception exception) {
        httpErrorStatus = HttpStatus.BAD_REQUEST;
        cause = "ошибка валидации";
        message = ((MethodArgumentNotValidException) exception)
                .getBindingResult()
                .getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
