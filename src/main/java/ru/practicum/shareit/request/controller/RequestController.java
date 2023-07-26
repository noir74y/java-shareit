package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.practicum.shareit.utils.AppConstants.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    public RequestDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                 @Valid @RequestBody RequestDtoReq dtoReq) throws Throwable {
        log.info("POST /requests/ requesterId={}, {}", requesterId, dtoReq);
        return requestService.create(requesterId, dtoReq);
    }

    @GetMapping
    public List<RequestDtoResp> findByUser(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId) {
        log.info("GET /requests/ requesterId={}", requesterId);
        return requestService.findByUser(requesterId);
    }
}
