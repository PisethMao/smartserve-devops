package co.istad.smartserve.features.promotion.menuitem.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PromotionMenuItemResponse(
        UUID id,
        UUID promotionId,
        UUID menuItemId,
        String menuItemNameEn,
        String menuItemNameKh,
        BigDecimal price,
        Instant createdAt
) {
}
