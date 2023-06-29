package ru.practicum.shareit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionController(Exception exception) {
        AppException appException;

        if (exception instanceof NotFoundException)
            appException = (NotFoundException) exception;
        else if (exception instanceof DuplicateEmailException)
            appException = (DuplicateEmailException) exception;
        else if (exception instanceof MethodArgumentNotValidException)
            appException = new ValidationException(exception);
        else
            appException = new OtherException(exception);

        return ResponseEntity
                .status(appException.getHttpErrorStatus())
                .body(appException.prepareErrorMessage());
    }
}
