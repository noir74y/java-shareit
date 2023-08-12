package ru.practicum.shareit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.clients.RequestClient;
import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.utils.AppConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static ru.practicum.shareit.utils.AppConfiguration.HEADER_USER_ID;

@Slf4j
@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
@Validated
public class RequestControllerGateway {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                         @Valid @RequestBody RequestDtoReq dtoReq) {
        log.info("requestorId={}, POST /requests/ {}", requestorId, dtoReq);
        return requestClient.create(requestorId, dtoReq);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByUser(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId) {
        log.info("requestorId={}, GET /requests/", requestorId);
        return requestClient.findAllByUser(requestorId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllByOthers(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                  @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, required = false, name = "from") @Min(0) Integer offset,
                                                  @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, required = false, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId={}, GET /requests/all?from={},size={}", requestorId, offset, pageSize);
        return requestClient.findAllByOthers(requestorId, offset, pageSize);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                           @PathVariable @NotNull Integer requestId) {
        log.info("requestorId={}, GET /requests/{}", requestorId, requestId);
        return requestClient.findById(requestorId, requestId);
    }
}
