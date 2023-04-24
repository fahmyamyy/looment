package com.looment.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String bio;

    @Column(nullable = false)
    private Date dob;

    private Integer otp;

    @Column(name = "otp_expired")
    private LocalDateTime otpExpired;

    @Column
    private Integer attemp = 0;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UsersInfo usersInfo;

    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Follows> followed;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Follows> follower;
}
