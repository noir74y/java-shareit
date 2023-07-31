package ru.practicum.shareit.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.model.CommentDtoResp;
import ru.practicum.shareit.item.model.ItemBooking;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JsonTest
public class ItemDtoTest {
    ItemDtoReq dtoReqObject;
    JsonContent<ItemDtoReq> dtoReqJsonContent;
    ItemDtoResp dtoRespObject;
    JsonContent<ItemDtoResp> dtoRespJsonContent;
    @Autowired
    private JacksonTester<ItemDtoReq> dtoReqTester;
    @Autowired
    private JacksonTester<ItemDtoResp> dtoRespTester;

    @Test
    void testDtoReq() throws IOException {
        dtoReqObject =  ItemDtoReq.builder()
                .name("Дрель")
                .description("Простая дрель")
                .available(true)
                .requestId(1).build();

        dtoReqJsonContent = dtoReqTester.write(dtoReqObject);
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.name").isEqualTo(dtoReqObject.getName());
        assertThat(dtoReqJsonContent).extractingJsonPathStringValue("$.description").isEqualTo(dtoReqObject.getDescription());
        assertThat(dtoReqJsonContent).extractingJsonPathBooleanValue("$.available").isEqualTo(dtoReqObject.getAvailable());
        assertThat(dtoReqJsonContent).extractingJsonPathNumberValue("$.requestId").isEqualTo(dtoReqObject.getRequestId());
    }

    @Test
    void testDtoReq_IncorrectMail() throws IOException {
        dtoReqObject =  ItemDtoReq.builder().name("").build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ItemDtoReq>> violations = validator.validate(dtoReqObject, OnCreate.class);
        assertEquals(violations.size(),3);
    }
}
