package ru.practicum.shareit.utils.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.utils.error.exception.*;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionController(Exception exception) {
        ShareItException shareItException;

        if (exception instanceof NotFoundException)
            shareItException = (NotFoundException) exception;
        else if (exception instanceof ForbiddenException)
            shareItException = (ForbiddenException) exception;
        else if (exception instanceof CustomValidationException)
            shareItException = (CustomValidationException) exception;
        else if (exception instanceof ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), "Unknown state: UNSUPPORTED_STATUS"));
        } else
            shareItException = new OtherException(exception);

        return ResponseEntity.status(shareItException.getHttpErrorStatus()).body(shareItException.prepareErrorMessage());
    }
}
