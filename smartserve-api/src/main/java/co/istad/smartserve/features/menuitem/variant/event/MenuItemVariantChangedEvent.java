package co.istad.smartserve.features.menuitem.variant.event;

import java.util.UUID;

public record MenuItemVariantChangedEvent(
        UUID menuItemId,
        UUID variantId,
        String action
) {
}
