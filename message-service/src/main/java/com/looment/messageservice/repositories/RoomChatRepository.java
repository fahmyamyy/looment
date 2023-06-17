package com.looment.messageservice.repositories;

import com.looment.messageservice.entities.RoomChats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoomChatRepository extends JpaRepository<RoomChats, UUID> {
    @Query("SELECT rc " +
            "FROM RoomChats rc " +
            "WHERE (LOWER(rc.receiver) = LOWER(:receiver)) " +
            "OR (LOWER(rc.sender) = LOWER(:receiver)) " +
            "AND rc.deletedAt IS NULL " +
            "ORDER BY rc.updatedAt DESC")
    Page<RoomChats> findByReceiverOrSenderAndDeletedAtIsNull(@Param("receiver")String receiver, Pageable pageable);
    @Query("SELECT rc " +
            "FROM RoomChats rc " +
            "WHERE (LOWER(rc.receiver) = LOWER(:receiver) AND LOWER(rc.sender) = LOWER(:sender)) " +
            "OR (LOWER(rc.sender) = LOWER(:receiver) AND LOWER(rc.receiver) = LOWER(:sender)) " +
            "AND rc.deletedAt IS NULL")
    Optional<RoomChats> findByReceiverAndSenderIgnoreCase(@Param("receiver") String receiver, @Param("sender") String sender);
}
