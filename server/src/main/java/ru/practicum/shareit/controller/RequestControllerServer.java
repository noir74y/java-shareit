package ru.practicum.shareit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.model.request.RequestDtoResp;
import ru.practicum.shareit.service.RequestService;
import ru.practicum.shareit.util.mapper.RequestMapper;
import ru.practicum.shareit.utils.AppConfiguration;

import java.util.List;

import static ru.practicum.shareit.utils.AppConfiguration.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestControllerServer {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    public RequestDtoResp create(@RequestHeader(HEADER_USER_ID) Integer requestorId,
                                 @RequestBody RequestDtoReq dtoReq) throws Throwable {
        log.info("requestorId={}, POST /requests/ {}", requestorId, dtoReq);
        return requestService.create(requestorId, dtoReq);
    }

    @GetMapping
    public List<RequestDtoResp> findAllByUser(@RequestHeader(HEADER_USER_ID) Integer requestorId) {
        log.info("requestorId={}, GET /requests/", requestorId);
        return requestService.findAllByUser(requestorId);
    }

    @GetMapping("/all")
    public List<RequestDtoResp> findAllByOthers(@RequestHeader(HEADER_USER_ID) Integer requestorId,
                                                @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, name = "from") Integer offset,
                                                @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, name = "size") Integer pageSize) {
        log.info("requestorId={}, GET /requests/all?from={},size={}", requestorId, offset, pageSize);
        return requestService.findAllByOthers(requestorId, offset, pageSize);
    }

    @GetMapping("/{requestId}")
    public RequestDtoResp findById(@RequestHeader(HEADER_USER_ID) Integer requestorId,
                                   @PathVariable Integer requestId) {
        log.info("requestorId={}, GET /requests/{}", requestorId, requestId);
        return requestService.findById(requestorId, requestId);
    }
}
