package ru.practicum.shareit.utils.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE_USE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = BookingDatesValidator.class)
public @interface BookingDatesConstraint {
    String message() default "End of booking has to be later than start of booking and the both is not null ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
