package com.looment.messageservice.controllers;

import com.looment.messageservice.dtos.BaseResponse;
import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.PaginationResponse;
import com.looment.messageservice.dtos.roomchats.responses.RoomChatResponse;
import com.looment.messageservice.services.roomchats.RoomChatService;
import com.looment.messageservice.utils.BaseController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/roomchat")
@RequiredArgsConstructor
public class RoomChatController extends BaseController {
    private final RoomChatService roomChatService;

    @GetMapping("{userId}" )
    public PaginationResponse<List<RoomChatResponse>> getRoomChats(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID userId
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<RoomChatResponse>, Pagination> pagination = roomChatService.getRoomChats(userId, pageable);
        return responsePagination("Successfully fetch RoomChats", pagination.getLeft(), pagination.getRight());
    }

    @DeleteMapping("{roomChatId}")
    public ResponseEntity<BaseResponse> deleteRoomChat(@PathVariable UUID roomChatId) {
        roomChatService.deleteRoomChat(roomChatId);
        return responseDelete();
    }
}
