package co.istad.smartserve.features.promotion.menuitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromotionMenuItemRepository extends JpaRepository<PromotionMenuItem, UUID> {
    @EntityGraph(attributePaths = {"promotion", "menuItem"})
    Page<PromotionMenuItem> findByPromotion_IdAndDeletedFalse(UUID promotionId, Pageable pageable);

    @EntityGraph(attributePaths = {"promotion", "menuItem"})
    Optional<PromotionMenuItem> findByPromotion_IdAndMenuItem_IdAndDeletedFalse(
            UUID promotionId,
            UUID menuItemId
    );

    boolean existsByPromotion_IdAndMenuItem_IdAndDeletedFalse(
            UUID promotionId,
            UUID menuItemId
    );
}
