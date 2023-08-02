package ru.practicum.shareit.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.model.BookingDtoReq;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BookingDtoTest {
    BookingDtoReq dtoReqObject;
    JsonContent<BookingDtoReq> dtoReqJsonContent;
    @Autowired
    private JacksonTester<BookingDtoReq> dtoReqTester;

    @BeforeEach
    void setUp() {
        dtoReqObject = BookingDtoReq.builder()
                .itemId(1)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(2)).build();
    }

    @Test
    void testDtoReq_ItemIdIsNull() throws IOException {
        dtoReqObject.setItemId(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BookingDtoReq>> violations = validator.validate(dtoReqObject);

        assertEquals(new ArrayList<>(violations).get(0).getMessage(),
                "не должно равняться null");
    }

    @Test
    void testDtoReq_StartDateIsInThePast() throws IOException {
        dtoReqObject.setStartDate(LocalDateTime.now().minusDays(1));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BookingDtoReq>> violations = validator.validate(dtoReqObject);

        assertEquals(new ArrayList<>(violations).get(0).getMessage(),
                "должно содержать сегодняшнее число или дату, которая еще не наступила");
    }

    @Test
    void testDtoReq_EndDateIsInThePast() throws IOException {
        dtoReqObject.setEndDate(LocalDateTime.now().minusDays(1));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BookingDtoReq>> violations = validator.validate(dtoReqObject);

        assertEquals(new ArrayList<>(violations).get(0).getMessage(),
                "должно содержать дату, которая еще не наступила");
    }
}
