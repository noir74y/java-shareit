package ru.practicum.shareit.utils.exception;

import org.springframework.http.HttpStatus;

public class WrongUserException extends AppException {
    public WrongUserException(Integer wrongUserId) {
        httpErrorStatus = HttpStatus.FORBIDDEN;
        this.cause = "wrong user";
        this.message = String.valueOf(wrongUserId);
    }
}

