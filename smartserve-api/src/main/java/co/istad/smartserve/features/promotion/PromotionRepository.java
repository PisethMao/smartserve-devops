package co.istad.smartserve.features.promotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    @EntityGraph(attributePaths = {"restaurant", "categories", "menuItems"})
    Optional<Promotion> findByIdAndDeletedFalse(UUID id);

    Page<Promotion> findByRestaurant_IdAndDeletedFalse(UUID restaurantId, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "categories", "menuItems"})
    @Query("""
            SELECT p FROM Promotion p
            WHERE p.restaurant.id = :restaurantId
              AND p.deleted = false
              AND p.status = true
              AND p.startAt <= :now
              AND p.endAt >= :now
            ORDER BY p.createdAt DESC
            """)
    List<Promotion> findActivePromotions(
            @Param("restaurantId") UUID restaurantId,
            @Param("now") Instant now
    );

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Promotion p
            JOIN p.categories c
            WHERE p.deleted = false
              AND p.status = true
              AND p.restaurant.id = :restaurantId
              AND p.promotionScope = :promotionScope
              AND c.id = :categoryId
              AND (:excludePromotionId IS NULL OR p.id <> :excludePromotionId)
              AND p.startAt < :endAt
              AND p.endAt > :startAt
            """)
    boolean existsActiveOverlappingCategoryPromotion(
            @Param("restaurantId") UUID restaurantId,
            @Param("categoryId") UUID categoryId,
            @Param("promotionScope") PromotionScope promotionScope,
            @Param("startAt") Instant startAt,
            @Param("endAt") Instant endAt,
            @Param("excludePromotionId") UUID excludePromotionId
    );

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Promotion p
            JOIN p.menuItems m
            WHERE p.deleted = false
              AND p.status = true
              AND p.restaurant.id = :restaurantId
              AND p.promotionScope = :promotionScope
              AND m.id = :menuItemId
              AND (:excludePromotionId IS NULL OR p.id <> :excludePromotionId)
              AND p.startAt < :endAt
              AND p.endAt > :startAt
            """)
    boolean existsActiveOverlappingMenuItemPromotion(
            @Param("restaurantId") UUID restaurantId,
            @Param("menuItemId") UUID menuItemId,
            @Param("promotionScope") PromotionScope promotionScope,
            @Param("startAt") Instant startAt,
            @Param("endAt") Instant endAt,
            @Param("excludePromotionId") UUID excludePromotionId
    );

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Promotion p
            WHERE p.deleted = false
              AND p.status = true
              AND p.restaurant.id = :restaurantId
              AND p.promotionScope = :promotionScope
              AND (:excludePromotionId IS NULL OR p.id <> :excludePromotionId)
              AND p.startAt < :endAt
              AND p.endAt > :startAt
            """)
    boolean existsActiveOverlappingOrderPromotion(
            @Param("restaurantId") UUID restaurantId,
            @Param("promotionScope") PromotionScope promotionScope,
            @Param("startAt") Instant startAt,
            @Param("endAt") Instant endAt,
            @Param("excludePromotionId") UUID excludePromotionId
    );
}
