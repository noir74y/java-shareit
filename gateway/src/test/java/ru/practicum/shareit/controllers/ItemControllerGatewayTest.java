package ru.practicum.shareit.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.clients.ItemClient;
import ru.practicum.shareit.model.comment.CommentDtoReq;
import ru.practicum.shareit.model.comment.CommentDtoResp;
import ru.practicum.shareit.model.item.ItemDtoReq;
import ru.practicum.shareit.model.item.ItemDtoResp;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemControllerGateway.class)
@Import({RestMockGeneric.class})
public class ItemControllerGatewayTest {
    private final String baseUrl = "/items/";
    @Autowired
    RestMockGeneric<ItemDtoReq, ItemDtoResp> itemRestMock;
    @Autowired
    RestMockGeneric<CommentDtoReq, CommentDtoResp> commentRestMock;
    int requestorId = 1;
    @MockBean
    private ItemClient client;
    private ItemDtoReq itemDtoReq;
    private ItemDtoResp itemDtoResp;
    private ResponseEntity<Object> itemResponseEntity;
    private CommentDtoReq commentDtoReq;
    private CommentDtoResp commentDtoResp;
    private ResponseEntity<Object> commentResponseEntity;


    @BeforeEach
    void setUp() {
        itemDtoReq = ItemDtoReq.builder().name("name").description("description").available(true).build();
        itemDtoResp = ItemDtoResp.builder().id(1).name(itemDtoReq.getName()).description(itemDtoReq.getDescription()).available(itemDtoReq.getAvailable()).build();
        itemResponseEntity = new ResponseEntity<Object>(itemDtoResp, HttpStatus.OK);

        commentDtoReq = CommentDtoReq.builder().text("comment").build();
        commentDtoResp = CommentDtoResp.builder().text(commentDtoReq.getText()).build();
        commentResponseEntity = new ResponseEntity<Object>(commentDtoResp, HttpStatus.OK);
    }

    @Test
    void createItem() throws Throwable {
        when(client.create(anyInt(), any())).thenReturn(itemResponseEntity);

        assertThat(
                itemRestMock.post(baseUrl, itemDtoReq, ItemDtoResp.class, requestorId),
                equalTo(itemDtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).create(anyInt(), any());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void createComment() throws Throwable {
        when(client.create(anyInt(), anyInt(), any())).thenReturn(commentResponseEntity);
        var itemId = 1;

        assertThat(
                commentRestMock.post(baseUrl + itemId + "/comment", commentDtoReq, CommentDtoResp.class, requestorId),
                equalTo(commentDtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).create(anyInt(), anyInt(), any());
        Mockito.verifyNoMoreInteractions(client);
    }


    @Test
    void update() throws Throwable {
        when(client.update(anyInt(), anyInt(), any())).thenReturn(itemResponseEntity);

        assertThat(
                itemRestMock.patch(baseUrl + itemDtoResp.getId(), itemDtoReq, ItemDtoResp.class, requestorId),
                equalTo(itemDtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).update(anyInt(), anyInt(), any());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findById() throws Throwable {
        when(client.findById(anyInt(), anyInt())).thenReturn(itemResponseEntity);

        assertThat(
                itemRestMock.get(baseUrl + itemDtoResp.getId(), ItemDtoResp.class, requestorId),
                equalTo(itemDtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).findById(anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findByOwner() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(itemDtoResp), HttpStatus.OK);

        when(client.findByOwner(anyInt())).thenReturn(referenceDtoRespList);

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        itemRestMock.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1)).findByOwner(anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findByText() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(itemDtoResp), HttpStatus.OK);

        when(client.findByText(anyInt(), anyString())).thenReturn(referenceDtoRespList);

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        itemRestMock.get(baseUrl + "search?text=SomePattern", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1)).findByText(anyInt(), anyString());
        Mockito.verifyNoMoreInteractions(client);
    }

}
