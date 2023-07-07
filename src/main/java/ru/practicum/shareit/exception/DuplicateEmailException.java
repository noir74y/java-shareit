package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends AppException {
    public DuplicateEmailException(String email) {
        httpErrorStatus = HttpStatus.CONFLICT;
        this.cause = "duplicate email";
        this.message = email;
    }
}
