package co.istad.smartserve.features.promotion.category.event;

import java.util.UUID;

public record PromotionCategoryChangedEvent(
        UUID promotionCategoryId,
        UUID promotionId,
        UUID categoryId,
        String action
) {
}
