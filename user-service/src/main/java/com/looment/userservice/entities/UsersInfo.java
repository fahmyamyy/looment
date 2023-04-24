package com.looment.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersInfo {
    @Id
    @Column(name = "users_id")
    private UUID id;

    @Column(nullable = false)
    private Integer followers = 0;

    @Column(nullable = false)
    private Integer followings = 0;

    @Column(name = "total_likes",nullable = false)
    private Integer totalLikes = 0;

    @Column(name = "total_posts", nullable = false)
    private Integer totalPosts = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "users_id")
    private Users users;
}