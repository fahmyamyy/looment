package com.looment.messageservice.repositories;

import com.looment.loomententity.entities.Messages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepositories extends JpaRepository<Messages, UUID> {
    Optional<Messages> findByReceiverEqualsIgnoreCaseAndSenderEqualsIgnoreCaseAndDeletedAtIsNull(String receiver, String sender);
    Optional<Messages> findMessagesByIdAndDeletedAtIsNull(UUID messageId);
    Page<Messages> findByRoomChats_IdOrderByCreatedAtDesc(UUID roomChatId, Pageable pageable);
    @Query("SELECT m " +
            "FROM Messages m " +
            "WHERE (LOWER(m.receiver) = LOWER(:receiver) AND LOWER(m.sender) = LOWER(:sender)) " +
            "OR (LOWER(m.sender) = LOWER(:receiver) AND LOWER(m.receiver) = LOWER(:sender)) " +
            "AND m.deletedAt IS NULL")
    Page<Messages> findByReceiverAndSenderIgnoreCase(@Param("receiver") String receiver, @Param("sender") String sender, Pageable pageable);
}
