package co.istad.smartserve.features.promotion.dto;

import co.istad.smartserve.features.promotion.PromotionDiscountType;
import co.istad.smartserve.features.promotion.PromotionScope;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PromotionResponse(
        UUID id,
        UUID restaurantId,
        String titleEn,
        String titleKh,
        String descriptionEn,
        String descriptionKh,
        PromotionDiscountType discountType,
        BigDecimal discountValue,
        PromotionScope promotionScope,
        Instant startAt,
        Instant endAt,
        Boolean status,
        Set<UUID> categoryIds,
        Set<UUID> menuItemIds,
        Instant createdAt,
        Instant updatedAt
) {
}
