package ru.practicum.shareit.json;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JsonTest
public class UserDtoTest {
    UserDtoReq dtoReqObject;
    JsonContent<UserDtoReq> dtoReqJsonContent;
    UserDtoResp dtoRespObject;
    JsonContent<UserDtoResp> dtoRespJsonContent;
    @Autowired
    private JacksonTester<UserDtoReq> dtoReqTester;
    @Autowired
    private JacksonTester<UserDtoResp> dtoRespTester;

    @Test
    void testDtoReq() throws IOException {
        dtoReqObject = UserDtoReq.builder().name("user").email("user@mail.com").build();
        dtoReqJsonContent = dtoReqTester.write(dtoReqObject);
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.name").isEqualTo(dtoReqObject.getName());
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.email").isEqualTo(dtoReqObject.getEmail());
    }

    @Test
    void testDtoReq_IncorrectMail() throws IOException {

        dtoReqObject = UserDtoReq.builder().name("user").email("IncorrectMail").build();
        dtoReqJsonContent = dtoReqTester.write(dtoReqObject);
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.name").isEqualTo(dtoReqObject.getName());
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.email").isEqualTo(dtoReqObject.getEmail());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDtoReq>> violations = validator.validate(dtoReqObject, OnCreate.class);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testDtoResp() throws IOException {
        dtoRespObject = UserDtoResp.builder().id(1).name("user").email("user@mail.com").build();
        dtoRespJsonContent = dtoRespTester.write(dtoRespObject);
        assertThat(dtoRespJsonContent).extractingJsonPathNumberValue("$.id").isEqualTo(dtoRespObject.getId());
        assertThat(dtoRespJsonContent).extractingJsonPathStringValue("$.name").isEqualTo(dtoRespObject.getName());
        assertThat(dtoRespJsonContent).extractingJsonPathStringValue("$.email").isEqualTo(dtoRespObject.getEmail());
    }
}