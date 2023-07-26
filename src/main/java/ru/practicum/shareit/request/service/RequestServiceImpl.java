package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestEntity;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestDtoResp create(Integer requesterId, RequestDtoReq dtoReq) throws Throwable {
        checkUser(requesterId);
        ;
        return requestMapper.entity2dtoResp(
                requestRepository.save(requestMapper.dtoReq2entity(dtoReq, requesterId))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByUser(Integer requesterId) {
        checkUser(requesterId);
        return requestMapper.bulkEntity2dtoResp(
                requestRepository.findAllByRequesterIdOrderByCreatedDesc(requesterId),
                requesterId
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByOthers(Integer requesterId, Integer offset, Integer pageSize) {
        checkUser(requesterId);

        if (Objects.isNull(offset) || Objects.isNull(pageSize))
            return Collections.emptyList();

        List<RequestEntity> entityList = requestRepository
                .findAllByRequesterIdNotOrderByCreatedDesc(requesterId, PageRequest.of(offset, pageSize))
                .toList();

        return requestMapper.bulkEntity2dtoResp(entityList);
    }

    @Override
    public RequestDtoResp findById(Integer requesterId, Integer requestId) {
        checkUser(requesterId);

        if (!requestRepository.existsById(requestId))
            throw new NotFoundException("no such request", String.valueOf(requestId));

        return null;
    }

    private void checkUser(Integer requesterId) {
        if (!userRepository.existsById(requesterId))
            throw new NotFoundException("no such user", String.valueOf(requesterId));
    }
}
