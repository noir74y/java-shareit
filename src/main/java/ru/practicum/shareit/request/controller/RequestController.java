package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.utils.AppConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.practicum.shareit.utils.AppConfiguration.HEADER_USER_ID;


@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    public RequestDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                 @Valid @RequestBody RequestDtoReq dtoReq) throws Throwable {
        log.info("requestorId={}, POST /requests/ {}", requestorId, dtoReq);
        return requestService.create(requestorId, dtoReq);
    }

    @GetMapping
    public List<RequestDtoResp> findAllByUser(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId) {
        log.info("requestorId={}, GET /requests/", requestorId);
        return requestService.findAllByUser(requestorId);
    }

    @GetMapping("/all")
    public List<RequestDtoResp> findAllByOthers(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, required = false, name = "from") @Min(0) Integer offset,
                                                @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, required = false, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId={}, GET /requests/all?from={},size={}", requestorId, offset, pageSize);
        return requestService.findAllByOthers(requestorId, offset, pageSize);
    }

    @GetMapping("/{requestId}")
    public RequestDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                   @PathVariable @NotNull Integer requestId) {
        log.info("requestorId={}, GET /requests/{}", requestorId, requestId);
        return requestService.findById(requestorId, requestId);
    }
}
