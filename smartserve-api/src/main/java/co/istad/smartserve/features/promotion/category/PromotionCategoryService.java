package co.istad.smartserve.features.promotion.category;

import co.istad.smartserve.features.promotion.category.dto.AttachPromotionCategoryRequest;
import co.istad.smartserve.features.promotion.category.dto.PromotionCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PromotionCategoryService {
    PromotionCategoryResponse attachCategory(
            UUID promotionId,
            AttachPromotionCategoryRequest request
    );

    Page<PromotionCategoryResponse> getCategoriesByPromotion(
            UUID promotionId,
            Pageable pageable
    );

    void detachByPromotionCategoryId(UUID promotionCategoryId);

    void detachByPromotionIdAndCategoryId(UUID promotionId, UUID categoryId);
}
