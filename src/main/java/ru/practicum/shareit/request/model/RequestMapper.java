package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;

    public RequestEntity dtoReq2entity(RequestDtoReq dtoReq, Integer requestorId) {
        var entity = modelMapper.map(dtoReq, RequestEntity.class);
        Optional.ofNullable(entity).ifPresent(obj -> {
            obj.setCreated(LocalDateTime.now());
            obj.setRequestorId(requestorId);
        });
        return entity;
    }

    public RequestDtoResp entity2dtoResp(RequestEntity entity) {
        return modelMapper.map(entity, RequestDtoResp.class);
    }
}
