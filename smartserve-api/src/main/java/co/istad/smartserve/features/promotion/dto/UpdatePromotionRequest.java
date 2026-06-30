package co.istad.smartserve.features.promotion.dto;

import co.istad.smartserve.features.promotion.PromotionDiscountType;
import co.istad.smartserve.features.promotion.PromotionScope;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UpdatePromotionRequest(
        @Size(max = 255, message = "Promotion title in English must not exceed 255 characters")
        String titleEn,
        @Size(max = 255, message = "Promotion title in Khmer must not exceed 255 characters")
        String titleKh,
        String descriptionEn,
        String descriptionKh,
        PromotionDiscountType discountType,
        @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
        BigDecimal discountValue,
        PromotionScope promotionScope,
        Instant startAt,
        Instant endAt,
        Boolean status,
        Set<UUID> categoryIds,
        Set<UUID> menuItemIds
) {
}
