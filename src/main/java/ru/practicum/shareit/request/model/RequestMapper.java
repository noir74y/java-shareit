package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;

    public RequestEntity dtoReq2entity(RequestDtoReq dtoReq, Integer requesterId) {
        var entity = modelMapper.map(dtoReq, RequestEntity.class);
        Optional.ofNullable(entity).ifPresent(obj -> {
            obj.setCreated(LocalDateTime.now());
            obj.setRequesterId(requesterId);
        });
        return entity;
    }

    public RequestDtoResp entity2dtoResp(RequestEntity entity) {
        return modelMapper.map(entity, RequestDtoResp.class);
    }

    public List<RequestDtoResp> bulkEntity2dtoResp(List<RequestEntity> entityList) {
        var dtoRespList = entityList.stream()
                .map(this::entity2dtoResp)
                .map(obj->{
                    return obj;})
                .collect(Collectors.toCollection(LinkedList::new));
        return dtoRespList;
    }

}
