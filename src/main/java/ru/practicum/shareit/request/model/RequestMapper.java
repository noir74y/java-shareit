package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

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
        return entityList.stream()
                .map(this::entity2dtoResp)
                .peek(dtoResp -> dtoResp.setItems(itemMapper.bulkEntity2dtoResp(itemRepository.findAllByRequestId(dtoResp.getId()))))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
