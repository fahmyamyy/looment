package com.looment.userservice.entities;

import com.looment.userservice.utils.RandomUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_follow_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowsRequest {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Users followed;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Users follower;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
