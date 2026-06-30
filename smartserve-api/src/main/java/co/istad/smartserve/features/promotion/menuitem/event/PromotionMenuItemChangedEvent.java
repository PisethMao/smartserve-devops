package co.istad.smartserve.features.promotion.menuitem.event;

import java.util.UUID;

public record PromotionMenuItemChangedEvent(
        UUID promotionId,
        UUID menuItemId,
        String action
) {
}
