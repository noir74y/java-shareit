package ru.practicum.shareit.utils.validation;

import ru.practicum.shareit.model.booking.BookingDtoReq;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesConstraint, BookingDtoReq> {
    @Override
    public void initialize(BookingDatesConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingDtoReq dtoReq, ConstraintValidatorContext constraintValidatorContext) {
        var startDate = dtoReq.getStartDate();
        var endDate = dtoReq.getEndDate();
        return !Objects.isNull(startDate) && !Objects.isNull(endDate) && startDate.isBefore(endDate);
    }
}