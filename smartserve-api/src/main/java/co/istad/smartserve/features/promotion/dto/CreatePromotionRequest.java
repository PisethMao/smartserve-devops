package co.istad.smartserve.features.promotion.dto;

import co.istad.smartserve.features.promotion.PromotionDiscountType;
import co.istad.smartserve.features.promotion.PromotionScope;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CreatePromotionRequest(
        @NotBlank(message = "Promotion title in English is required")
        @Size(max = 255, message = "Promotion title in English must not exceed 255 characters")
        String titleEn,
        @Size(max = 255, message = "Promotion title in Khmer must not exceed 255 characters")
        String titleKh,
        String descriptionEn,
        String descriptionKh,
        @NotNull(message = "Discount type is required")
        PromotionDiscountType discountType,
        @NotNull(message = "Discount value is required")
        @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
        BigDecimal discountValue,
        @NotNull(message = "Promotion scope is required")
        PromotionScope promotionScope,
        @NotNull(message = "Start date is required")
        Instant startAt,
        @NotNull(message = "End date is required")
        Instant endAt,
        Boolean status,
        Set<UUID> categoryIds,
        Set<UUID> menuItemIds
) {
}
