package ru.practicum.shareit.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.model.comment.CommentDtoReq;
import ru.practicum.shareit.model.item.ItemDtoReq;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> create(Integer requestorId, ItemDtoReq dtoReq) {
        return post("", requestorId.longValue(), dtoReq);
    }

    public ResponseEntity<Object> create(Integer requestorId, Integer itemId, CommentDtoReq dtoReq) {
        return post("/" + itemId + "/comment", requestorId.longValue(), dtoReq);
    }

    public ResponseEntity<Object> update(Integer requestorId, Integer itemId, ItemDtoReq itemDto) {
        return patch("/" + itemId, requestorId.longValue(), itemDto);
    }

    public ResponseEntity<Object> findById(Integer requestorId, Integer itemId) {
        return get("/" + itemId, requestorId.longValue());
    }

    public ResponseEntity<Object> findByOwner(Integer requestorId) {
        return get("", requestorId.longValue());
    }

    public ResponseEntity<Object> findByText(Integer requestorId, String text) {
        return get("/search?text={text}", requestorId.longValue(), Map.of("text", text));
    }
}