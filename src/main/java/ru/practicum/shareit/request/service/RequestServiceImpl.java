package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestEntity;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestEntity create(Integer requesterId, RequestDtoReq dtoReq) throws Throwable {
        if (!userRepository.existsById(requesterId))
            throw new NotFoundException("no such user", String.valueOf(requesterId));

        return requestRepository.save(requestMapper.dtoReq2entity(dtoReq, requesterId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestEntity> findByUser(Integer requesterId) {
        if (!userRepository.existsById(requesterId))
            throw new NotFoundException("no such user", String.valueOf(requesterId));

        return requestRepository.findAllByRequesterId(requesterId);
    }
}
