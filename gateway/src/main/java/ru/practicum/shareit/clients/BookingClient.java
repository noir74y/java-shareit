package ru.practicum.shareit.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.model.booking.BookingDtoReq;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> create(Integer requestorId, BookingDtoReq dtoReq) {
        return post("", requestorId.longValue(), dtoReq);
    }

    public ResponseEntity<Object> update(Integer requestorId, Integer bookingId, Boolean approved) {
        return patch("/" + bookingId + "?approved=" + approved, requestorId.longValue());
    }

    public ResponseEntity<Object> findById(Integer requestorId, Integer bookingId) {
        return get("/" + bookingId, requestorId.longValue());
    }

    public ResponseEntity<Object> findByBookerAndState(Integer requestorId, String state, int offset, int pageSize) {
        return get("?state={state}&from={from}&size={size}", requestorId.longValue(), Map.of("from", offset, "size", pageSize, "state", state));
    }

    public ResponseEntity<Object> findByOwnerAndState(Integer requestorId, String state, int offset, int pageSize) {
        return get("/owner?state={state}&from={from}&size={size}", requestorId.longValue(), Map.of("from", offset, "size", pageSize, "state", state));
    }

}