package ru.practicum.shareit.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionController(Exception exception) {
        AppException appException;

        if (exception instanceof NotFoundException)
            appException = (NotFoundException) exception;
        else if (exception instanceof DuplicateEmailException)
            appException = (DuplicateEmailException) exception;
        else if (exception instanceof ForbiddenException)
            appException = (ForbiddenException) exception;
        else if (exception instanceof CustomValidationException)
            appException = (CustomValidationException) exception;
        else if (exception instanceof WrongUserException)
            appException = (WrongUserException) exception;
        else if (exception instanceof MethodArgumentNotValidException)
            appException = new GenericValidationException(exception);
        else if (exception instanceof MissingRequestHeaderException) {
            appException = new HeaderMissingException(exception);
        } else
            appException = new OtherException(exception);

        return ResponseEntity
                .status(appException.getHttpErrorStatus())
                .body(appException.prepareErrorMessage());
    }
}
