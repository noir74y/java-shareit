package ru.practicum.shareit.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.item.controller.ItemController;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.UserMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
@Import({RestMockGeneric.class, ItemMapper.class, ModelMapper.class, UserMapper.class, CommentMapper.class})
public class ItemControllerTest {
    private final String baseUrl = "/items/";
    @Autowired
    RestMockGeneric<ItemDtoReq, ItemDtoResp> restMock;
    int requestorId = 1;
    @MockBean
    private ItemService service;
    private ItemDtoReq dtoReq;
    private Item model;
    private ItemDtoResp dtoResp;

    @BeforeEach
    void setUp() {
        dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        model = Item.builder().id(1).name(dtoReq.getName()).description(dtoReq.getDescription()).available(dtoReq.getAvailable()).build();
        dtoResp = ItemDtoResp.builder().id(1).name(dtoReq.getName()).description(dtoReq.getDescription()).available(dtoReq.getAvailable()).build();
    }

    @Test
    void create() throws Throwable {
        when(service.create(anyInt(), any())).thenReturn(model);

        assertThat(
                restMock.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).create(anyInt(), any());
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void update() throws Throwable {
        when(service.update(anyInt(), any(), anyInt())).thenReturn(model);

        assertThat(
                restMock.patch(baseUrl + dtoResp.getId(), dtoReq, ItemDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).update(anyInt(), any(), anyInt());
        Mockito.verifyNoMoreInteractions(service);
    }

//
//    @Test
//    void find() throws Throwable {
//        when(service.findById(userId)).thenReturn(model);
//
//        assertThat(
//                restMock.get(baseUrl + userId, UserDtoResp.class),
//                equalTo(dtoResp)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).findById(userId);
//        Mockito.verifyNoMoreInteractions(service);
//    }
//
//    @Test
//    void findAll() throws Exception {
//        List<User> referenceModelList = List.of(model);
//        List<UserDtoResp> referenceDtoRespList = List.of(dtoResp);
//
//        when(service.findAll()).thenReturn(referenceModelList);
//
//        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
//                .readValue(
//                        restMock.get(baseUrl),
//                        new TypeReference<>() {
//                        });
//
//        assertThat(
//                dtoRespList,
//                equalTo(referenceDtoRespList)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).findAll();
//        Mockito.verifyNoMoreInteractions(service);
//    }

}
