package co.istad.smartserve.features.promotion.category.dto;

import java.time.Instant;
import java.util.UUID;

public record PromotionCategoryResponse(
        UUID id,
        UUID promotionId,
        UUID categoryId,
        String categoryNameEn,
        String categoryNameKh,
        Instant createdAt,
        Instant updatedAt
) {
}
