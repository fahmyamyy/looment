package com.looment.userservice.repositories;

import com.looment.loomententity.entities.Follows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follows, UUID> {
//    @Query(value = "SELECT NEW com.looment.loomententity.entities.Follows(" +
//            "bai.id AS id, " +
//            "i.id AS identifierId, " +
//            "i.name AS identifierType, " +
//            "i.value AS identifier, " +
//            "bai.createDate AS createDate, " +
//            "bai.updateDate AS updateDate, " +
//            "bai.isDeleted AS isDeleted, " +
//            "bai.createdBy AS createdBy, " +
//            "bai.updatedBy AS updatedBy) " +
//            "FROM BankAccountIdentifier bai " +
//            "JOIN bai.identifier i " +
//            "JOIN i.paymentChannel pc " +
//            "JOIN bai.bankAccount ba " +
//            "WHERE ba = :bankAccount " +
//            "AND i.value LIKE %:identifier% " +
//            "AND i.name LIKE %:identifierType% " +
//            "AND (:isDeleted IS NULL OR bai.isDeleted IS :isDeleted)")
    @Query(value = "SELECT * FROM users_follow " +
            "WHERE followed_id = :userId " +
            "AND is_request IS FALSE " +
            "AND deleted_at IS NULL " +
            "ORDER BY updated_at DESC", nativeQuery = true)
    Page<Follows> findFollowers(Pageable pageable, @Param("userId") UUID userId);
    @Query(value = "SELECT * FROM users_follow " +
            "WHERE follower_id = :userId " +
            "AND is_request IS FALSE " +
            "AND deleted_at IS NULL " +
            "ORDER BY updated_at DESC", nativeQuery = true)
    Page<Follows> findFollowings(Pageable pageable, @Param("userId") UUID userId);
    Page<Follows> findByFollowed_IdEqualsAndIsRequestIsTrue(Pageable pageable, UUID userId);
    Optional<Follows> findByFollower_IdEquals(UUID userId);
//    Optional<Follows> findByFollowed_IdEqualsAndIsRequestIsTrue(Pageable pageable, UUID userId);
    Optional<Follows> findByFollowed_IdEqualsAndFollower_IdEquals(UUID followedId, UUID followersId);
}
