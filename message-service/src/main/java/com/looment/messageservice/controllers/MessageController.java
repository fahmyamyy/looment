package com.looment.messageservice.controllers;

import com.looment.messageservice.dtos.BaseResponse;
import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.PaginationResponse;
import com.looment.messageservice.dtos.messages.requests.MessageRequest;
import com.looment.messageservice.dtos.messages.responses.MessageInfoResponse;
import com.looment.messageservice.dtos.messages.responses.MessageResponse;
import com.looment.messageservice.services.messages.MessageService;
import com.looment.messageservice.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/message")
@RequiredArgsConstructor
public class MessageController extends BaseController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<BaseResponse> sendMessage(@RequestBody @Valid MessageRequest messageRequest) {
        MessageResponse messageResponse = messageService.sendMessage(messageRequest);
        return responseSuccess("Successfully send a Message", messageResponse);
    }

    @GetMapping("{roomChatId}" )
    public PaginationResponse<List<MessageInfoResponse>> getMessages(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @PathVariable UUID roomChatId
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<MessageInfoResponse>, Pagination> pagination = messageService.getMessages(roomChatId, pageable);
        return responsePagination("Successfully fetch Messages", pagination.getLeft(), pagination.getRight());
    }

    @DeleteMapping("{messageId}" )
    public ResponseEntity<BaseResponse> getFollowings(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
        return responseDelete();
    }
}
