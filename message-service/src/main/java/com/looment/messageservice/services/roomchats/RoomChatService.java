package com.looment.messageservice.services.roomchats;

import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.roomchats.responses.RoomChatResponse;
import com.looment.messageservice.entities.RoomChats;
import com.looment.messageservice.exceptions.roomchats.RoomChatNotExists;
import com.looment.messageservice.repositories.RoomChatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomChatService implements IRoomChatService {
    private final RoomChatRepository roomChatRepository;
    private final ModelMapper modelMapper;

    @Override
    @Cacheable(value = "roomChats")
    public Pair<List<RoomChatResponse>, Pagination> getRoomChats(UUID userId, Pageable pageable) {
        Page<RoomChats> roomChatsPage = roomChatRepository.findByReceiverOrSenderAndDeletedAtIsNull(userId.toString(), pageable);

        List<RoomChatResponse> roomChatResponseList = roomChatsPage.stream()
                .map(rc -> modelMapper.map(rc, RoomChatResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                roomChatsPage.getTotalPages(),
                roomChatsPage.getTotalElements(),
                roomChatsPage.getNumber() + 1
        );
        return new ImmutablePair<>(roomChatResponseList, pagination);

    }

    @Override
    @CacheEvict(value = "roomChats", allEntries = true)
    public void deleteRoomChat(UUID roomChatId) {
        RoomChats roomChats = roomChatRepository.findById(roomChatId)
                .orElseThrow(RoomChatNotExists::new);

        roomChats.setDeletedAt(LocalDateTime.now());
        roomChatRepository.save(roomChats);
    }
}
