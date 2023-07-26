package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestEntity;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.Collections;
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
    public RequestDtoResp create(Integer requestorId, RequestDtoReq dtoReq) throws Throwable {
        checkUser(requestorId);
        ;
        return requestMapper.entity2dtoResp(
                requestRepository.save(requestMapper.dtoReq2entity(dtoReq, requestorId))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByUser(Integer requestorId) {
        checkUser(requestorId);
        return requestMapper.bulkEntity2dtoResp(
                requestRepository.findAllByRequestorIdOrderByCreatedDesc(requestorId),
                requestorId
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDtoResp> findAllByOthers(Integer requestorId, Integer offset, Integer pageSize) {
        checkUser(requestorId);

        if (Objects.isNull(offset) || Objects.isNull(pageSize))
            return Collections.emptyList();

        List<RequestEntity> entityList = requestRepository
                .findAllByRequestorIdNotOrderByCreatedDesc(requestorId, PageRequest.of(offset, pageSize))
                .toList();

        return requestMapper.bulkEntity2dtoResp(entityList);
    }

    @Override
    public RequestDtoResp findById(Integer requestorId, Integer requestId) {
        checkUser(requestorId);

        if (!requestRepository.existsById(requestId))
            throw new NotFoundException("no such request", String.valueOf(requestId));

        return null;
    }

    private void checkUser(Integer requestorId) {
        if (!userRepository.existsById(requestorId))
            throw new NotFoundException("no such user", String.valueOf(requestorId));
    }
}
