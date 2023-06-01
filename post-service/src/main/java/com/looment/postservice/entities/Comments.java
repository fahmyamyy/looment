package com.looment.postservice.entities;

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
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String users;

    @Column(nullable = false, updatable = false)
    private String comment;

    @Column(name = "total_likes", insertable = false, updatable = false)
    private Integer totalLikes = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Posts posts;

    @OneToMany(mappedBy = "comments", fetch = FetchType.LAZY, orphanRemoval = true)
    @Where(clause = "deleted_at IS NULL")
    private List<CommentLikes> commentLikes;

    public Integer getTotalLikes() {
        if (commentLikes != null) {
            return commentLikes.size();
        }
        return 0;
    }
}
