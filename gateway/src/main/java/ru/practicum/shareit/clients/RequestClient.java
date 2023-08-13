package ru.practicum.shareit.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.model.request.RequestDtoReq;

import java.util.Map;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> create(Integer requestorId, RequestDtoReq dtoReq) {
        return post("", requestorId.longValue(), dtoReq);
    }

    public ResponseEntity<Object> findAllByUser(Integer requestorId) {
        return get("", requestorId.longValue());
    }

    public ResponseEntity<Object> findAllByOthers(Integer requestorId, int offset, int pageSize) {
        return get("/all?from={from}&size={size}", requestorId.longValue(), Map.of("from", offset, "size", pageSize));
    }

    public ResponseEntity<Object> findById(Integer requestorId, Integer requestId) {
        return get("/" + requestId, requestorId.longValue());
    }
}