package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.ItemForRequestView;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

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

    public List<RequestDtoResp> bulkEntity2dtoResp(List<RequestEntity> requestEntities) {
        Set<Integer> requestIdSet = requestEntities.stream().map(RequestEntity::getId).collect(Collectors.toSet());

        Map<Integer, List<ItemEntity>> itemEntitiesByRequestId = itemRepository
                .findAllByRequestIdIn(requestIdSet)
                .stream()
                .collect(Collectors.groupingBy(ItemEntity::getRequestId));

        return requestEntities.stream()
                .map(this::entity2dtoResp)
                .peek(dtoResp -> Optional.ofNullable(dtoResp)
                        .ifPresent(obj -> obj.setItems(itemMapper.bulkEntity2dtoResp(itemEntitiesByRequestId.get(obj.getId())))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<RequestDtoResp> bulkEntity2dtoResp(List<RequestEntity> entityList, Integer requestorId) {
        Map<Integer, List<ItemForRequestView>> itemsViewByRequestId = itemRepository
                .findAllByRequesterId(requestorId)
                .stream()
                .collect(Collectors.groupingBy(ItemForRequestView::getRequestId));

        return entityList.stream()
                .map(this::entity2dtoResp)
                .peek(dtoResp -> Optional
                        .ofNullable(dtoResp)
                        .ifPresent(obj -> obj.setItems(convert2itemsDtoResp(itemsViewByRequestId.get(obj.getId())))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<ItemDtoResp> convert2itemsDtoResp(List<ItemForRequestView> inList) {
        if (Objects.isNull(inList))
            return Collections.emptyList();
        else
            return inList.stream().map(ItemForRequestView::getItemDtoResp).collect(Collectors.toList());
    }
}
