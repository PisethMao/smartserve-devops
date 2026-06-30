package co.istad.smartserve.features.promotion.event;

import java.util.UUID;

public record PromotionChangedEvent(
        UUID promotionId,
        UUID restaurantId,
        String action
) {
}
