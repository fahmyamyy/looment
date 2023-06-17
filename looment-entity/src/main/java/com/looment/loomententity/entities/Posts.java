package com.looment.loomententity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String users;

//    @Column(nullable = false, updatable = false)
//    private String url;

    private String caption;

    private String location;

    @Column(nullable = false)
    private Boolean commentable = true;

    @Column(name = "total_likes")
    private Integer totalLikes = 0;

    @Column(name = "total_comments")
    private Integer totalComments = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, orphanRemoval = true)
    @Where(clause = "deleted_at IS NULL")
    private List<Likes> likes;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, orphanRemoval = true)
    @Where(clause = "deleted_at IS NULL")
    private List<Comments> comments;

    public Integer getTotalComments() {
        if (comments != null) {
            return comments.size();
        }
        return 0;
    }

    public Integer getTotalLikes() {
        if (likes != null) {
            return likes.size();
        }
        return 0;
    }
}
