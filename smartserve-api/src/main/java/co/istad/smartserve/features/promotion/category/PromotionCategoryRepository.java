package co.istad.smartserve.features.promotion.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromotionCategoryRepository extends JpaRepository<PromotionCategory, UUID> {
    @EntityGraph(attributePaths = {"promotion", "category"})
    Optional<PromotionCategory> findByIdAndDeletedFalse(UUID id);

    @EntityGraph(attributePaths = {"promotion", "category"})
    Page<PromotionCategory> findByPromotion_IdAndDeletedFalse(UUID promotionId, Pageable pageable);

    boolean existsByPromotion_IdAndCategory_IdAndDeletedFalse(UUID promotionId, UUID categoryId);

    @EntityGraph(attributePaths = {"promotion", "category"})
    Optional<PromotionCategory> findByPromotion_IdAndCategory_IdAndDeletedFalse(
            UUID promotionId,
            UUID categoryId
    );
}
