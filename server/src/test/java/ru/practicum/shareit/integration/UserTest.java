package ru.practicum.shareit.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.model.user.UserDtoReq;
import ru.practicum.shareit.model.user.UserDtoResp;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserTest {
    private final String baseUrl = "/users/";
    @Autowired
    RestMockGeneric<UserDtoReq, UserDtoResp> rest;

    @Test
    void create() throws Exception {
        var dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();

        assertThat(
                rest.post(baseUrl, dtoReq, UserDtoResp.class),
                equalTo(UserDtoResp.builder()
                        .id(1)
                        .name(dtoReq.getName())
                        .email(dtoReq.getEmail()).build())
        );
    }

    @Test
    void update() throws Exception {
        var dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        var dtoResp = rest.post(baseUrl, dtoReq, UserDtoResp.class);

        dtoReq.setName("update");
        dtoReq.setEmail("update@user.com");

        assertThat(
                rest.patch(baseUrl + dtoResp.getId(), dtoReq, UserDtoResp.class),
                equalTo(UserDtoResp.builder()
                        .id(dtoResp.getId())
                        .name(dtoReq.getName())
                        .email(dtoReq.getEmail()).build())
        );
    }

    @Test
    void delete() throws Exception {
        var dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        var dtoResp = rest.post(baseUrl, dtoReq, UserDtoResp.class);
        rest.delete(baseUrl + dtoResp.getId());

        Assertions.assertThrows(
                AssertionError.class,
                () -> rest.get(baseUrl + dtoResp.getId(), UserDtoResp.class));
    }

    @Test
    void find() throws Exception {
        var dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        var dtoResp = rest.post(baseUrl, dtoReq, UserDtoResp.class);

        assertThat(
                rest.get(baseUrl + dtoResp.getId(), UserDtoResp.class),
                equalTo(dtoResp)
        );
    }

    @Test
    void findAll() throws Exception {
        var dtoReq1 = UserDtoReq.builder().name("user1").email("use1r@user.com").build();
        var dtoResp1 = rest.post(baseUrl, dtoReq1, UserDtoResp.class);
        var dtoReq2 = UserDtoReq.builder().name("user2").email("user2@user.com").build();
        var dtoResp2 = rest.post(baseUrl, dtoReq2, UserDtoResp.class);

        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(dtoResp1, dtoResp2))
        );
    }
}
