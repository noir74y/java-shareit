package ru.practicum.shareit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.model.item.ItemDtoResp;
import ru.practicum.shareit.model.item.ItemEntity;
import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.model.request.RequestDtoResp;
import ru.practicum.shareit.model.request.RequestEntity;
import ru.practicum.shareit.repository.ItemForRequestView;
import ru.practicum.shareit.repository.ItemRepository;
import ru.practicum.shareit.repository.RequestRepository;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.RequestService;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.util.mapper.ItemMapper;
import ru.practicum.shareit.util.mapper.RequestMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public RequestDtoResp create(Integer requestorId, RequestDtoReq dtoReq) throws Throwable {
        checkUser(requestorId);

        return requestMapper.entity2dtoResp(
                requestRepository.save(requestMapper.dtoReq2entity(dtoReq, requestorId))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByUser(Integer requestorId) {
        checkUser(requestorId);

        List<RequestEntity> requestEntities = requestRepository.findAllByRequestorIdOrderByCreatedDesc(requestorId);

        Map<Integer, List<ItemForRequestView>> itemsViewByRequestId = itemRepository
                .findAllByRequesterId(requestorId)
                .stream()
                .collect(Collectors.groupingBy(ItemForRequestView::getRequestId));

        return requestEntities.stream()
                .map(requestMapper::entity2dtoResp)
                .peek(dtoResp -> Optional
                        .ofNullable(dtoResp)
                        .ifPresent(obj -> obj.setItems(convert2itemsDtoResp(itemsViewByRequestId.get(obj.getId())))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByOthers(Integer requestorId, Integer offset, Integer pageSize) {
        checkUser(requestorId);

        List<RequestEntity> requestEntities = requestRepository
                .findAllByRequestorIdNotOrderByCreatedDesc(requestorId, PageRequest.of(offset / pageSize, pageSize))
                .toList();

        Set<Integer> requestIdSet = requestEntities.stream().map(RequestEntity::getId).collect(Collectors.toSet());

        Map<Integer, List<ItemEntity>> itemEntitiesByRequestId = itemRepository
                .findAllByRequestIdIn(requestIdSet)
                .stream()
                .collect(Collectors.groupingBy(ItemEntity::getRequestId));

        return requestEntities.stream()
                .map(requestMapper::entity2dtoResp)
                .peek(dtoResp -> Optional.ofNullable(dtoResp)
                        .ifPresent(obj -> obj.setItems(itemMapper.bulkEntity2dtoResp(itemEntitiesByRequestId.get(obj.getId())))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDtoResp findById(Integer requestorId, Integer requestId) {
        checkUser(requestorId);

        var dtoResp = requestRepository.findById(requestId)
                .map(requestMapper::entity2dtoResp)
                .orElseThrow(() -> new NotFoundException("no such request", String.valueOf(requestId)));

        dtoResp.setItems(itemMapper.bulkEntity2dtoResp(itemRepository.findAllByRequestId(requestId)));

        return dtoResp;
    }

    private void checkUser(Integer requestorId) {
        if (!userRepository.existsById(requestorId))
            throw new NotFoundException("no such user", String.valueOf(requestorId));
    }

    private List<ItemDtoResp> convert2itemsDtoResp(List<ItemForRequestView> inList) {
        if (Objects.isNull(inList))
            return Collections.emptyList();
        else
            return inList.stream().map(ItemForRequestView::getItemDtoResp).collect(Collectors.toList());
    }
}
