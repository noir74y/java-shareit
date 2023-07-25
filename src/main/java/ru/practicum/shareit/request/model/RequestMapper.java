package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;

    public Request dtoReq2model(RequestDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Request.class)).orElse(null);
    }

    public RequestDtoResp model2dtoResp(Request model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, RequestDtoResp.class)).orElse(null);
    }

    public RequestEntity model2entity(Request model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, RequestEntity.class)).orElse(null);
    }

    public Request entity2model(RequestEntity entity) {
        return Optional.ofNullable(entity).map(obj -> modelMapper.map(obj, Request.class)).orElse(null);
    }

    public ArrayList<RequestDtoResp> bulkModel2dtoResp(Collection<Request> models) {
        return models.stream().map(this::model2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Request> bulkEntity2model(Collection<RequestEntity> entities) {
        return entities.stream().map(this::entity2model).collect(Collectors.toCollection(ArrayList::new));
    }
}
