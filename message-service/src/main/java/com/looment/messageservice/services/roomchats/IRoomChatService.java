package com.looment.messageservice.services.roomchats;

import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.roomchats.responses.RoomChatResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IRoomChatService {
    Pair<List<RoomChatResponse>, Pagination> getRoomChats(UUID userId, Pageable pageable);
    void deleteRoomChat(UUID roomChatId);
}
