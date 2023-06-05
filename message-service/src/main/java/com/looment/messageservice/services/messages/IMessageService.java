package com.looment.messageservice.services.messages;

import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.messages.requests.MessageRequest;
import com.looment.messageservice.dtos.messages.responses.MessageInfoResponse;
import com.looment.messageservice.dtos.messages.responses.MessageResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IMessageService {
    MessageResponse sendMessage(MessageRequest messageRequest);
    Pair<List<MessageInfoResponse>, Pagination> getMessages(UUID roomChatId, Pageable pageable);
    void deleteMessage(UUID messageId);
}
