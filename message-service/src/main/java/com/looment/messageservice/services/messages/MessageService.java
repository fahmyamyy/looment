package com.looment.messageservice.services.messages;

import com.looment.messageservice.dtos.Pagination;
import com.looment.messageservice.dtos.messages.requests.MessageRequest;
import com.looment.messageservice.dtos.messages.responses.MessageInfoResponse;
import com.looment.messageservice.dtos.messages.responses.MessageResponse;
import com.looment.messageservice.entities.Messages;
import com.looment.messageservice.entities.RoomChats;
import com.looment.messageservice.exceptions.messages.MessageNotExists;
import com.looment.messageservice.exceptions.roomchats.RoomChatNotExists;
import com.looment.messageservice.repositories.MessageRepositories;
import com.looment.messageservice.repositories.RoomChatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final MessageRepositories messageRepositories;
    private final RoomChatRepository roomChatRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        RoomChats roomChats;

        if (messageRequest.getRoomChat().isEmpty()) {
            roomChats = roomChatRepository.findByReceiverAndSenderIgnoreCase(messageRequest.getReceiver(), messageRequest.getSender())
                    .orElseGet(() -> {
                        RoomChats newRoomChat = new RoomChats();
                        newRoomChat.setReceiver(messageRequest.getReceiver());
                        newRoomChat.setSender(messageRequest.getSender());
                        return newRoomChat;
                    });
        } else {
            roomChats = roomChatRepository.findById(UUID.fromString(messageRequest.getRoomChat()))
                    .orElseThrow(RoomChatNotExists::new);
        }

        Messages messages = new Messages();
        messages.setReceiver(messageRequest.getReceiver());
        messages.setSender(messageRequest.getSender());
        messages.setMessage(messageRequest.getMessage());
        messages.setRoomChats(roomChats);

        roomChats.setUpdatedAt(LocalDateTime.now());

        roomChatRepository.save(roomChats);
        messageRepositories.save(messages);

        return modelMapper.map(messages, MessageResponse.class);
    }

    @Override
    public Pair<List<MessageInfoResponse>, Pagination> getMessages(UUID roomChatId, Pageable pageable) {
        Page<Messages> messagesPage = messageRepositories.findByRoomChats_IdOrderByCreatedAtDesc(roomChatId, pageable);

        List<MessageInfoResponse> messageResponseList = messagesPage.stream()
                .map(message -> modelMapper.map(message, MessageInfoResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                messagesPage.getTotalPages(),
                messagesPage.getTotalElements(),
                messagesPage.getNumber() + 1
        );
        return new ImmutablePair<>(messageResponseList, pagination);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        Messages messages = messageRepositories.findMessagesByIdAndDeletedAtIsNull(messageId)
                .orElseThrow(MessageNotExists::new);

        messages.setMessage("This Message has been deleted");
        messages.setDeletedAt(LocalDateTime.now());
        messageRepositories.save(messages);
    }
}
